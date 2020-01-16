package com.xnj.ticketgrab.service.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.xnj.ticketgrab.domain.Ticket;
import com.xnj.ticketgrab.domain.TicketFactory;
import com.xnj.ticketgrab.service.TicketService;
import com.xnj.ticketgrab.service.UserService;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String ticketIdPrefix = "T_ID_";
    private static final String ticketNoPrefix = "G000000";

    @Resource(name = "stringRedisTemplate")
    StringRedisTemplate stringRedisTemplate;
    @Resource(name = "redisTemplate")
    RedisTemplate<String, Ticket> ticketRepo;
    @Autowired
    UserService userService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        this.ticketRepo = redisTemplate;
    }

    @Override
    public Ticket genTicket() {
        final String globalTicketNoKey = "globalTicketNo";
        if (!stringRedisTemplate.hasKey(globalTicketNoKey)) {
            stringRedisTemplate.opsForValue().set(globalTicketNoKey, "0");
        }
        Long ticketNo = stringRedisTemplate.opsForValue().increment(globalTicketNoKey);

        final String formatTicketId = ticketIdPrefix + ticketNo;
        final String formatTicketNo = ticketNoPrefix + ticketNo;

        Ticket ticket = TicketFactory.getTicket(formatTicketId,
                formatTicketNo.substring(formatTicketNo.length() - ticketNoPrefix.length()));
        ticketRepo.opsForValue().set(formatTicketId.toString(), ticket);
        return ticket;
    }

    @Override
    public boolean existTicket(String ticketId) {
        return ticketRepo.hasKey(ticketId);
    }

    @Override
    public boolean ticketSold(String ticketId) {
        if (!existTicket(ticketId)) {
            throw new RuntimeException("车票不存在");
        }
        return ((Ticket) ticketRepo.opsForValue().get(ticketId)).isSold();
    }

    @Override
    public List<Ticket> getTicketList() {
        Set<String> keys = ticketRepo.keys(ticketIdPrefix + "*");
        List<Ticket> tickets = ticketRepo.opsForValue().multiGet(keys);
        return tickets;
    }

    @Override
    public synchronized boolean buyTicket(String ticketId) {
        if (!existTicket(ticketId)) {
            return false;
        }
        if (ticketSold(ticketId)) {
            ticketRepo.expire(ticketId, 0, TimeUnit.MICROSECONDS);
            return false;
        }
        Ticket ticket = (Ticket) ticketRepo.opsForValue().get(ticketId);
        ticket.setSold(true);
        ticketRepo.expire(ticketId, 0, TimeUnit.MICROSECONDS);
        userService.countUser(Thread.currentThread().getName());
        return true;
    }

}

package com.xnj.ticketgrab.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.xnj.ticketgrab.domain.Constant;
import com.xnj.ticketgrab.domain.User;
import com.xnj.ticketgrab.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Resource(name = "stringRedisTemplate")
    RedisTemplate<String, String> userRedisTemplate;

    @Override
    public List<User> getUserList() {
        final BoundZSetOperations<String, String> boundZSetOps = userRedisTemplate
                .boundZSetOps(Constant.CONSUMER_PREFIX);
        Set<TypedTuple<String>> rangeByScoreWithScores = boundZSetOps.rangeByScoreWithScores(0, Integer.MAX_VALUE);
        List<User> list = new ArrayList<>();
        for (TypedTuple<String> typedTuple : rangeByScoreWithScores) {
            User user = new User();
            String value = typedTuple.getValue();
            Double score = typedTuple.getScore();
            user.setName(value);
            user.setScore(score.intValue());
            list.add(user);
        }
        return list;
    }

    @Override
    public void countUser(String userName) {
        final BoundZSetOperations<String, String> boundZSetOps = userRedisTemplate
                .boundZSetOps(Constant.CONSUMER_PREFIX);
        boundZSetOps.incrementScore(userName, 1);
    }

}

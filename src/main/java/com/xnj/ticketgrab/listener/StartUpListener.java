package com.xnj.ticketgrab.listener;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.xnj.ticketgrab.domain.Constant;
import com.xnj.ticketgrab.domain.Ticket;
import com.xnj.ticketgrab.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(value = "startup.listener.enable", havingValue = "true")
@Component
@Slf4j
public class StartUpListener implements CommandLineRunner {

    @Autowired
    TicketService ticketService;

    private static final int interval = 3000;

    @Override
    public void run(String... args) throws Exception {
        ExecutorService consumerPool = Executors.newFixedThreadPool(25);
        ExecutorService producerPool = Executors.newFixedThreadPool(10);
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            final int index = i;
            consumerPool.execute(() -> {
                Thread.currentThread().setName(Constant.CONSUMER_PREFIX + "-" + index);
                while (true) {
                    try {
                        List<Ticket> ticketList = ticketService.getTicketList();
                        int size = ticketList.size();
                        if (size == 0) {
                            log.info("All ticket has been sold out.");
                            continue;
                        }
                        Ticket ticket = ticketList.get(random.nextInt(size));
                        if (ticketService.buyTicket(ticket.getId())) {
                            log.info("Buy ticket succeed. Ticket: {}.", ticket);
                        } else {
                            log.error("Ticket has been sold out. Ticket: {}", ticket);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(200);
        }

        for (int i = 0; i < 10; ++i) {
            final int index = i;
            producerPool.submit(() -> {
                Thread.currentThread().setName(Constant.PRODUCER_PREFIX + "-" + index);
                while (true) {
                    try {
                        log.info("Generate new ticket: {}", ticketService.genTicket());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(200);
        }
    }

}

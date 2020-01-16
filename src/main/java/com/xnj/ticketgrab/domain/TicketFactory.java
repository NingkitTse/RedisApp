package com.xnj.ticketgrab.domain;

import java.util.Date;

/**
 * 车票工厂——工厂模式
 * @author w24882 xieningjie
 * @date 2020年1月15日
 */
public class TicketFactory {

    private TicketFactory instance = new TicketFactory();

    private TicketFactory() {
    }

    public TicketFactory getInstance() {
        return this.instance;
    }

    public static Ticket getTicket(String id, String ticketNo) {
        Ticket ticket = new Ticket();
        ticket.setGenDate(new Date());
        ticket.setId(id);
        ticket.setTicketNo(ticketNo);
        return ticket;
    }

}

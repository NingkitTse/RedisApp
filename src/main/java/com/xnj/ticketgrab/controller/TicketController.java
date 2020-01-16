package com.xnj.ticketgrab.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xnj.ticketgrab.domain.Ticket;
import com.xnj.ticketgrab.service.TicketService;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Resource
    TicketService service;

    @GetMapping(value = "infos")
    public List<Ticket> infos() {
        return service.getTicketList();
    }

}

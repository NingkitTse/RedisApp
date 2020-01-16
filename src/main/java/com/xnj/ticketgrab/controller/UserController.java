package com.xnj.ticketgrab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xnj.ticketgrab.domain.User;
import com.xnj.ticketgrab.service.UserService;

@RequestMapping(value = "user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "infos")
    public List<User> infos() {
        return userService.getUserList();
    }

}

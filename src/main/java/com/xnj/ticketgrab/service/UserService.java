package com.xnj.ticketgrab.service;

import java.util.List;

import com.xnj.ticketgrab.domain.User;

/**
 * 用户相关服务类
 * 
 * @author w24882 xieningjie
 * @date 2020年1月16日
 */
public interface UserService {

    /**
     * 获取用户列表，观察每个用户购买的票数
     * 
     * @return
     * @author w24882 xieningjie
     * @date 2020年1月16日
     */
    List<User> getUserList();

    /**
     * 用户购票成功计数
     * 
     * @param userName
     * @author w24882 xieningjie
     * @date 2020年1月16日
     */
    void countUser(String userName);

}

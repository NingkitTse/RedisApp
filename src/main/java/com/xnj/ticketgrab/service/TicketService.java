package com.xnj.ticketgrab.service;

import java.util.List;

import com.xnj.ticketgrab.domain.Ticket;

/**
 * 车票相关服务类
 * 
 * @author w24882 xieningjie
 * @date 2020年1月16日
 */
public interface TicketService {

    /**
     * 生成票
     * 
     * @author w24882 xieningjie
     * @date 2020年1月15日
     */
    Ticket genTicket();

    /**
     * 获取车票列表
     * 
     * @return
     * @author w24882 xieningjie
     * @date 2020年1月15日
     */
    List<Ticket> getTicketList();

    /**
     * 检查车票是否存在
     * 
     * @param ticketId
     * @return
     * @author w24882 xieningjie
     * @date 2020年1月15日
     */
    boolean existTicket(String ticketId);

    /**
     * 判断车票是否被售出
     * 
     * @param ticketId
     * @return
     * @author w24882 xieningjie
     * @date 2020年1月15日
     */
    boolean ticketSold(String ticketId);

    /**
     * 购买车票
     * 
     * @param ticketId Ticket唯一ID
     * @return 是否购买成功
     * @author w24882 xieningjie
     * @date 2020年1月16日
     */
    boolean buyTicket(String ticketId);
}

package com.xnj.ticketgrab.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Ticket implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -410758939240973971L;

    private String id;

    private Date genDate;

    private String ticketNo;

    private boolean sold;

}

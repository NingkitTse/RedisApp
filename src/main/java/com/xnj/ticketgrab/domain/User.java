package com.xnj.ticketgrab.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4776524376945642687L;

    private String name;

    private int score;

    public void increScore() {
        this.score++;
    }

}

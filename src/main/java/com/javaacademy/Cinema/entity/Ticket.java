package com.javaacademy.Cinema.entity;

import lombok.Data;

@Data
public class Ticket {
    private Integer id;
    private Session session;
    private Place place;
    private boolean isPaid;
}

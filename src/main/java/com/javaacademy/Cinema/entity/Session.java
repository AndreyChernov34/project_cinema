package com.javaacademy.Cinema.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Session {
    private Integer id;
    private Timestamp datetime;
    private BigDecimal price;
    private Movie movie;
}

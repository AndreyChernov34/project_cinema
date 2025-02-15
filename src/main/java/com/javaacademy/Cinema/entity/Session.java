package com.javaacademy.Cinema.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Session {
    private Integer id;
    private LocalDateTime datetime;
    private BigDecimal price;
    private Movie movie;
}

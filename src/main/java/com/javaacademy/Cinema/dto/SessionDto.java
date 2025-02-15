package com.javaacademy.Cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionDto {
    private LocalDateTime datetime;
    private BigDecimal price;
    private Integer idMovie;
}

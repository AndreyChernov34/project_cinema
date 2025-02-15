package com.javaacademy.Cinema.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class SessionDto {
    private LocalDateTime datetime;
    private BigDecimal price;
    private Integer idMovie;
}

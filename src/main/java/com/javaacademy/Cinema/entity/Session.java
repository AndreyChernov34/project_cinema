package com.javaacademy.Cinema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Сущность сеанс")
public class Session {
    private Integer id;
    private LocalDateTime datetime;
    private BigDecimal price;
    private Movie movie;
}

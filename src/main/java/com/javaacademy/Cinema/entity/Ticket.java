package com.javaacademy.Cinema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность билет")
public class Ticket {
    private Integer id;
    private Session session;
    private Place place;
    private boolean isPaid;
}

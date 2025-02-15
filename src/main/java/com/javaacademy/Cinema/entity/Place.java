package com.javaacademy.Cinema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность место")
public class Place {
    private Integer id;
    private String name;
}

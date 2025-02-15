package com.javaacademy.Cinema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность фильм")
public class Movie {
    private Integer id;
    private String name;
    private String description;


}

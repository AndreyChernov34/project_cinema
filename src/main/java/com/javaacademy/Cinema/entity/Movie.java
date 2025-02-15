package com.javaacademy.Cinema.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Сущность фильм")
public class Movie {
    private Integer id;
    private String name;
    private String description;


}

package com.javaacademy.Cinema.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {
    private String name;
    private String description;
}

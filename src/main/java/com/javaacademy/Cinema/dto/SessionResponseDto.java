package com.javaacademy.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class SessionResponseDto {
    private Integer id;
    @JsonProperty("name_movie")
    private String nameMovie;
    @JsonProperty("date")
    private LocalDateTime datetime;
    private BigDecimal price;
}

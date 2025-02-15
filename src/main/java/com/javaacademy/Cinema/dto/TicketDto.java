package com.javaacademy.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TicketDto {
    @JsonProperty("session_id")
    private Integer sessionId;
    @JsonProperty("place_name")
    private String placeName;
}

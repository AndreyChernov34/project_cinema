package com.javaacademy.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponseDto {
    @JsonProperty("ticket_id")
    private Integer ticketId;
    @JsonProperty("place_name")
    private String placeNname;
    @JsonProperty("movie_name")
    private String movieName;
    @JsonProperty("date")
    private LocalDateTime dateTime;
}

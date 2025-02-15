package com.javaacademy.Cinema.controller;

import com.javaacademy.Cinema.dto.TicketDto;
import com.javaacademy.Cinema.dto.TicketResponseDto;
import com.javaacademy.Cinema.entity.Ticket;
import com.javaacademy.Cinema.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "TicketController", description = "Контроллер для работы с билетами")
public class TicketController {
    private final TicketService ticketService;
    @Value("${admin.token}")
    private String admintoken;

    @GetMapping("/ticket/saled")
    @Operation(summary = "Получение всех оплаченных билетов")
    public ResponseEntity<List<Ticket>> getAllPayTicket(
            @RequestHeader(value = "user-token", required = true) String token) {
        if ((!token.equals(admintoken)) || (token == null) || (token.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ticketService.getAllPayTicket(), HttpStatus.OK);
    }

    @GetMapping("/ticket")
    @Operation(summary = "Получение всех билетов")
    public ResponseEntity<List<Ticket>> getAllTicket(@RequestHeader("user-token") String token) {
        if (!token.equals(admintoken)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ticketService.getAllTicket(), HttpStatus.OK);
    }

    @PostMapping("/ticket/booking")
    @Operation(summary = "Оплата билета")
    public ResponseEntity<TicketResponseDto> payTicket(@RequestBody TicketDto ticketDto) {
        return new ResponseEntity<>(ticketService.payTicket(ticketDto), HttpStatus.OK);
    }

}

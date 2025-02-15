package com.javaacademy.Cinema.service;

import com.javaacademy.Cinema.dto.TicketDto;
import com.javaacademy.Cinema.dto.TicketResponseDto;
import com.javaacademy.Cinema.entity.Ticket;
import com.javaacademy.Cinema.exception.TicketNotFoundException;
import com.javaacademy.Cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    public List<Ticket> getAllPayTicket() {
        List<Ticket> ticketList = ticketRepository.getAllPayTicket();
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("Билетов не найдено");
        }
        return ticketList;
    }

    public List<Ticket> getAllTicket() {
        List<Ticket> ticketList = ticketRepository.getAllTicket();
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("Билетов не найдено");
        }
        return ticketList;
    }

    public List<Ticket> getAllPayTicketByIdSession(Integer id) {
        List<Ticket> ticketList = ticketRepository.getAllPayTicketByIdSession(id);
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("Билетов не найдено");
        }
        return ticketList;
    }

    public List<Ticket> getAllTicketByIdSession(Integer id) {
        List<Ticket> ticketList = ticketRepository.getAllTicketByIdSession(id);
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("Билетов не найдено");
        }
        return ticketList;
    }

    public TicketResponseDto payTicket(TicketDto ticketDto) {
        Optional<Ticket> ticketOptional = ticketRepository.
                findBySessionNamePlace(ticketDto.getSessionId(), ticketDto.getPlaceName());
        if (ticketOptional.isEmpty()) {
            throw new TicketNotFoundException("Такого билета не найдено");
        }

        Ticket ticket = ticketRepository.payTicket(ticketOptional.get().getId());
        return TicketResponseDto.builder()
                .ticketId(ticket.getId())
                .placeNname(ticket.getPlace().getName())
                .movieName(ticket.getSession().getMovie().getName())
                .dateTime(ticket.getSession().getDatetime())
                .build();
    }
}


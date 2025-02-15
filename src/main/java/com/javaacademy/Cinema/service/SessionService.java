package com.javaacademy.Cinema.service;

import com.javaacademy.Cinema.dto.PlaceDto;
import com.javaacademy.Cinema.dto.SessionDto;
import com.javaacademy.Cinema.dto.SessionResponseDto;
import com.javaacademy.Cinema.entity.Place;
import com.javaacademy.Cinema.entity.Session;
import com.javaacademy.Cinema.entity.Ticket;
import com.javaacademy.Cinema.exception.SessionNotFoundException;
import com.javaacademy.Cinema.exception.TicketNotFoundException;
import com.javaacademy.Cinema.repository.MovieRepository;
import com.javaacademy.Cinema.repository.PlaceRepository;
import com.javaacademy.Cinema.repository.SessionRepository;
import com.javaacademy.Cinema.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final MovieRepository movieRepository;


    public Session createSession(SessionDto sessionDto) {
        Session session = new Session();
        session.setPrice(sessionDto.getPrice());
        session.setDatetime(sessionDto.getDatetime());
        session.setMovie(movieRepository.findById(sessionDto.getIdMovie()).orElse(null));
        session = sessionRepository.createSession(session);
        List<Place> placeList = placeRepository.getAllPlace();
        Session newSession = session;
        placeList.stream().forEach(place -> {
            Ticket ticket = new Ticket();
            ticket.setSession(newSession);
            ticket.setPlace(place);
            ticket.setPaid(false);
            ticketRepository.createTicket(ticket);
        });
        return session;
    }

    public List<SessionResponseDto> getAllSession() {
        List<Session> sessionList = sessionRepository.getAllSession();
        if (sessionList.isEmpty()) {
            throw new SessionNotFoundException("Сеанс не найден");
        }

        List<SessionResponseDto> sessionResponseDtos = sessionList.stream()
                .map(session -> SessionResponseDto.builder()
                        .id(session.getId())
                        .nameMovie(session.getMovie().getName())
                        .datetime(session.getDatetime())
                        .price(session.getPrice())
                        .build()
                )
                .toList();
        return sessionResponseDtos;
    }

    public List<PlaceDto> getFreePlaceSession(Integer id) {
        List<Ticket> ticketList = ticketRepository.getAllNotPayTicketByIdSession(id);
        if (ticketList.isEmpty()) {
            throw new TicketNotFoundException("Билеты не найдены");
        }
        return ticketList.stream().map(ticket -> PlaceDto.builder()
                        .name(ticket.getPlace().getName())
                        .build()
                )
                .toList();
    }
}

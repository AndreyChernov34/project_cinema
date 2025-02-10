package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Session;
import com.javaacademy.Cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepository {
    final public JdbcTemplate jdbcTemplate;
    final public SessionRepository sessionRepository;
    final public PlaceRepository placeRepository;

    public Optional<Ticket> findById(Integer id) {
        String sql = "select * from ticket where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToTicket, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int RowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.set_pay(rs.getBoolean("is_pay"));
        if (rs.getString("id_session") != null) {
            Integer session_id = Integer.valueOf(rs.getString("id_session"));
            ticket.setSession(sessionRepository.findById(session_id).orElse(null));
        }
        if (rs.getString("id_place") != null) {
            Integer place_id = Integer.valueOf(rs.getString("id_place"));
            ticket.setPlace(placeRepository.findById(place_id).orElse(null));
        }


        return ticket;
    }

}

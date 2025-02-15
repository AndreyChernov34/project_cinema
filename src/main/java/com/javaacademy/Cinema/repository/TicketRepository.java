package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Ticket;
import com.javaacademy.Cinema.exception.TicketIsPaidException;
import com.javaacademy.Cinema.exception.TicketNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepository {
    public final JdbcTemplate jdbcTemplate;
    public final SessionRepository sessionRepository;
    public final PlaceRepository placeRepository;

    public List<Ticket> getAllTicketByIdSession(Integer idSession) {
        String sql = "select * from ticket t where t.id_session = ?";
        return jdbcTemplate.query(sql, this::mapToTicket, idSession);
    }

    public List<Ticket> getAllPayTicket() {
        String sql = "select * from ticket where is_paid = true";
        return jdbcTemplate.query(sql, this::mapToTicket);
    }

    public List<Ticket> getAllTicket() {
        String sql = "select * from ticket";
        return jdbcTemplate.query(sql, this::mapToTicket);
    }

    public List<Ticket> getAllPayTicketByIdSession(Integer idSession) {
        String sql = "select * from ticket t where t.id_session = ? and t.is_paid = true";
        return jdbcTemplate.query(sql, this::mapToTicket, idSession);
    }

    public List<Ticket> getAllNotPayTicketByIdSession(Integer idSession) {
        String sql = "select * from ticket t where t.id_session = ? and t.is_paid = false";
        return jdbcTemplate.query(sql, this::mapToTicket, idSession);
    }

    public Ticket payTicket(Integer id) {
        if (findById(id).isEmpty()) {
            throw new TicketNotFoundException("Билет с таким id не найден");
        } else {
            String sql = "update ticket set is_paid = true where id = ? and is_paid is false";
            if (jdbcTemplate.update(sql, id) > 0) {
                return findById(id).get();
            } else {
                throw new TicketIsPaidException("Билет уже оплачен");
            }
        }
    }

    public Ticket createTicket(Ticket ticket) {
        String sql = "insert into ticket (id_session, id_place) values (?, ?) returning id";
        Integer idTicket = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                ticket.getSession().getId(),
                ticket.getPlace().getId()
        );
        ticket.setId(idTicket);
        return ticket;
    }

    public Optional<Ticket> findById(Integer id) {
        String sql = "select * from ticket where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToTicket, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setPaid(rs.getBoolean("is_paid"));
        if (rs.getString("id_session") != null) {
            Integer idSession = Integer.valueOf(rs.getString("id_session"));
            ticket.setSession(sessionRepository.findById(idSession).orElse(null));
        }
        if (rs.getString("id_place") != null) {
            Integer idPlace = Integer.valueOf(rs.getString("id_place"));
            ticket.setPlace(placeRepository.findById(idPlace).orElse(null));
        }
        return ticket;
    }

    public Optional<Ticket> findBySessionNamePlace(Integer idSession, String namePlace) {
        String sql = """
                select *
                from ticket t join place p on t.id_place = p.id
                where id_session = ? and p.name = ?;
                """;
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToTicket, idSession, namePlace));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

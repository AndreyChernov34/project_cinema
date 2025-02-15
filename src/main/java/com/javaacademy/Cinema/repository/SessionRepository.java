package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Session;
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
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public List<Session> getAllSession() {
        String sql = "select * from session";
        return jdbcTemplate.query(sql, this::mapToSession);
    }

    public Session createSession(Session session) {
        String sql = " insert into session (datetime, price, id_movie ) values (?, ?, ?) returning id ";
        Integer idSession = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                session.getDatetime(),
                session.getPrice(),
                session.getMovie().getId()
        );
        session.setId(idSession);
        return session;
    }

    public Optional<Session> findById(Integer id) {
        String sql = "select * from session where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToSession, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Session mapToSession(ResultSet rs, int rowNum) {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setDatetime(rs.getTimestamp("datetime").toLocalDateTime());
        session.setPrice(rs.getBigDecimal("price"));
        if (rs.getString("id_movie") != null) {
            Integer idMovie = Integer.valueOf(rs.getString("id_movie"));
            session.setMovie(movieRepository.findById(idMovie).orElse(null));
        }
        return session;
    }
}

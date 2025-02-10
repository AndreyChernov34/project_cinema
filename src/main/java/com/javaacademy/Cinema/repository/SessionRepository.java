package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public Optional<Session> findById(Integer id) {
        String sql = "select * from session where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToSession, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Session mapToSession(ResultSet rs, int RowNum) {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setDatetime(rs.getTimestamp("date"));
        session.setPrice(rs.getBigDecimal("price"));
        if (rs.getString("id_movie") != null) {
            Integer movie_id = Integer.valueOf(rs.getInt("id_movie"));
            session.setMovie(movieRepository.findById(movie_id).orElse(null));
        }
        return session;
    }


}

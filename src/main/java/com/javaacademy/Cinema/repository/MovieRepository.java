package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Movie;
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
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public Movie createMovie(Movie movie) {
        String sql = "insert into movie (name, description) values (?, ?) returning id";
        Integer idMovie = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                movie.getName(),
                movie.getDescription()
        );
        movie.setId(idMovie);
        return movie;
    }

    public List<Movie> getAllMovie() {
        String sql = "select * from movie";
        return jdbcTemplate.query(sql, this::mapToMovie);
    }

    public Optional<Movie> findById(Integer id) {
        String sql = "select * from movie where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToMovie, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Movie mapToMovie(ResultSet rs, int rowNum) {
        Movie movie = Movie.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .build();

        return movie;
    }
}

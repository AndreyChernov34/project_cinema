package com.javaacademy.Cinema.repository;

import com.javaacademy.Cinema.entity.Place;
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
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Place> findById(Integer id) {
        String sql = "select * from place where id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, this::mapToPlace, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    private Place mapToPlace(ResultSet rs, int RowNum) {
        Place place = new Place();
        place.setId(rs.getInt("id"));
        place.setName(rs.getString("name"));
        return place;
    }


}

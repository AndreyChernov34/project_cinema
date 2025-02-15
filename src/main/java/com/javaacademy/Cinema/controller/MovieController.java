package com.javaacademy.Cinema.controller;

import com.javaacademy.Cinema.dto.MovieDto;
import com.javaacademy.Cinema.entity.Movie;
import com.javaacademy.Cinema.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/movie")
@RequiredArgsConstructor
@Tag(name = "MovieController", description = "Контроллер для работы с фильмами")
public class MovieController {
    private final MovieService movieService;

    @Value("${admin.token}")
    private String admintoken;

    @PostMapping
    @Operation(summary = "Создание нового фильма")
    public ResponseEntity<Movie> createMovie(@RequestHeader("user-token") String token,
                                             @RequestBody MovieDto movieDto) {
        if ((!token.equals(admintoken)) || (token == null) || (token.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Получение  всех фильмов")
    public ResponseEntity<List<MovieDto>> getMovie() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovie());
    }

}

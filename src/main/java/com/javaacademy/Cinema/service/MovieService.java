package com.javaacademy.Cinema.service;

import com.javaacademy.Cinema.dto.MovieDto;
import com.javaacademy.Cinema.entity.Movie;
import com.javaacademy.Cinema.exception.MovieNotFoundException;
import com.javaacademy.Cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        return movieRepository.createMovie(movie);
    }


    public List<MovieDto> getMovie() {
        List<Movie> movieList = movieRepository.getAllMovie();
        if (movieList.isEmpty()) {
            throw new MovieNotFoundException("Фильмы не найдены");
        }
        List<MovieDto> movieDtoList = movieList.stream()
                .map(movie -> MovieDto.builder()
                        .name(movie.getName())
                        .description(movie.getDescription())
                        .build()
                )
                .toList();
        return movieDtoList;
    }
}

package com.TraperRoku.controller;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.repository.MovieRepository;
import com.TraperRoku.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;


    @GetMapping("/schedule")
    public ResponseEntity<List<Movie>> getMoviesByDate(@RequestParam LocalDate date) {
        List<Movie> movies = movieService.getMoviesByDate(date);
        return ResponseEntity.ok(movies);
    }
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovieWithScheduleAndSeats(movie));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}


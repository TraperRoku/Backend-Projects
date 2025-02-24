package com.TraperRoku.controller;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.entity.MovieSchedule;
import com.TraperRoku.repository.MovieScheduleRepository;
import com.TraperRoku.service.MovieScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class MovieScheduleController {

    private final MovieScheduleService movieScheduleService;

    @GetMapping("/date")
    public ResponseEntity<List<MovieSchedule>> getScheduleByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(movieScheduleService.getSchedulesByDate(localDate));
    }

    // Nowy endpoint do pobierania seansów dla konkretnego filmu i dnia
    @GetMapping("/movie/{movieId}/date")
    public ResponseEntity<List<MovieSchedule>> getSchedulesForMovieByDate(
            @PathVariable Long movieId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(movieScheduleService.getSchedulesByMovieAndDate(movieId, localDate));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<MovieSchedule>> getSchedulesByMovie(
            @PathVariable Long movieId,
            @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return ResponseEntity.ok(movieScheduleService.getSchedulesByMovieAndDateRange(movieId, startOfDay, endOfDay));
    }
}


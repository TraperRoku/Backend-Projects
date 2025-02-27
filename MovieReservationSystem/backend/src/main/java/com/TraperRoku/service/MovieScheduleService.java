package com.TraperRoku.service;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.entity.MovieSchedule;
import com.TraperRoku.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieScheduleService {
    private final MovieScheduleRepository movieScheduleRepository;

    public List<MovieSchedule> getSchedulesByDate(LocalDate date) {
        return movieScheduleRepository.findByShowTimeBetween(
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );
    }

    public List<MovieSchedule> getSchedulesByMovieAndDate(Long movieId, LocalDate date) {
        return movieScheduleRepository.findByMovieIdAndShowTimeBetween(
                movieId,
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX)
        );
    }

    public List<MovieSchedule> getSchedulesByMovieAndDateRange(Long movieId, LocalDateTime start, LocalDateTime end) {
        return movieScheduleRepository.findByMovieIdAndShowTimeBetween(movieId, start, end);
    }
}
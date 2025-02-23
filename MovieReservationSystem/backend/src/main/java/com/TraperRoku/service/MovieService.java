package com.TraperRoku.service;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.entity.MovieSchedule;
import com.TraperRoku.entity.Seat;
import com.TraperRoku.enums.SeatCategory;
import com.TraperRoku.enums.SeatStatus;
import com.TraperRoku.repository.MovieRepository;
import com.TraperRoku.repository.MovieScheduleRepository;
import com.TraperRoku.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieScheduleRepository movieScheduleRepository;
    private final SeatRepository seatRepository;




    public List<Movie> getMoviesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // 23:59:59.999999999

        return movieScheduleRepository.findByShowTimeBetween(startOfDay, endOfDay).stream()
                .map(MovieSchedule::getMovie)
                .distinct()
                .collect(Collectors.toList());
    }
    @Transactional
    public Movie createMovieWithScheduleAndSeats(Movie movie) {
        // 1. Save the movie
        if (movie.getShowTime() != null) {
            movie.setShowTime(movie.getShowTime().truncatedTo(ChronoUnit.MINUTES));
        }
        Movie savedMovie = movieRepository.save(movie);

        // 2. Create schedules for specified dates
        List<MovieSchedule> schedules = new ArrayList<>();
        LocalDate startDate = movie.getStartDate();
        LocalDate endDate = movie.getEndDate();
        LocalTime showTime = movie.getShowTime(); // LocalTime

        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Nieprawidłowe daty filmu");
        }

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            MovieSchedule schedule = new MovieSchedule();
            schedule.setMovie(savedMovie);
            schedule.setShowTime(LocalDateTime.of(date, showTime)); // Combine date and time
            schedules.add(schedule);
        }
        movieScheduleRepository.saveAll(schedules);

        // 3. Create seats for each schedule
        List<Seat> seats = new ArrayList<>();
        for (MovieSchedule schedule : schedules) {
            for (int row = 1; row <= 5; row++) {
                for (int seatNum = 1; seatNum <= 10; seatNum++) {
                    Seat seat = new Seat();
                    seat.setMovieSchedule(schedule);
                    seat.setRowNumber(row);
                    seat.setSeatNumber(seatNum);
                    seat.setStatus(SeatStatus.AVAILABLE);
                    seat.setCategory(SeatCategory.STANDARD);
                    seat.setPrice(seat.getPrice());
                    seats.add(seat);
                }
            }
        }
        seatRepository.saveAll(seats);

        return savedMovie;
    }

    public Movie createMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
    public Movie getMovieById(Long id){
        return movieRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("There is not movie with this id"));
    }

    public void deleteMovieById(Long id){
        if(!movieRepository.existsById(id)){
            throw new IllegalStateException("There is not movie with this id");
        }

        movieRepository.deleteById(id);
    }
}

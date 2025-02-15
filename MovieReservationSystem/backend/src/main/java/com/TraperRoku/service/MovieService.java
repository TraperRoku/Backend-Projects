package com.TraperRoku.service;

import com.TraperRoku.entity.Movie;
import com.TraperRoku.repository.MovieRepository;
import com.TraperRoku.repository.MovieScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

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

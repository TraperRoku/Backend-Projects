package com.TraperRoku.backend.repository;

import com.TraperRoku.backend.entities.User;
import com.TraperRoku.backend.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    List<Workout> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Workout> findById(Long id);
    List<Workout> findByUser(User user);



}

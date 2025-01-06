package com.TraperRoku.backend.repository;

import com.TraperRoku.backend.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByWorkout_Id(Long workoutId);
    Exercise findByExerciseId(Long exerciseId);
}

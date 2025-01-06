package com.TraperRoku.backend.service;

import com.TraperRoku.backend.entities.Exercise;
import com.TraperRoku.backend.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository ;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<Exercise> getExercisesByWorkoutId(Long workoutId) {
        return exerciseRepository.findByWorkout_Id(workoutId);
    }

    public Exercise saveExercise(Exercise exercise){
        return exerciseRepository.save(exercise);
    }

    public Exercise findById(Long exerciseId){
        return exerciseRepository.findByExerciseId(exerciseId);
    }
    public void deleteById(Long exerciseId){
        exerciseRepository.deleteById(exerciseId);
    }
}

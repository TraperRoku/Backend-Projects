package com.TraperRoku.backend.service;


import com.TraperRoku.backend.entities.Exercise;
import com.TraperRoku.backend.entities.User;
import com.TraperRoku.backend.entities.Workout;
import com.TraperRoku.backend.repository.ExerciseRepository;
import com.TraperRoku.backend.repository.WorkoutRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class WorkoutService {
    @Autowired
    public WorkoutRepository workoutRepository;

    @Autowired
    public ExerciseRepository exerciseRepository;


    public Exercise addExerciseToWorkoutPlan(Long WorkoutId, Exercise exercise){
        Optional<Workout> workout = workoutRepository.findById(WorkoutId);
        if(workout.isPresent()){
            Workout workoutTo = workout.get();
            exercise.setWorkout(workoutTo);
            return exerciseRepository.save(exercise);
        }else{
            throw new RuntimeException("Workout Id with " + WorkoutId + " not found");
        }
    }

    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    public List<Workout> getWorkoutsForWeek(LocalDate weekStart) {
        LocalDate weekEnd = weekStart.plusDays(6);
        return workoutRepository.findAllByDateBetween(weekStart, weekEnd);
    }

    public List<Workout> getAllWorkouts(){
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(Long WorkoutId){
        return workoutRepository.findById(WorkoutId).orElse(null);
    }

    public Workout saveWorkout(Workout workout){
        return workoutRepository.save(workout);
    }

  public void deleteWorkout(Long Id){
        workoutRepository.deleteById(Id);
  }
    public List<Workout> getWorkoutsByUser(User user) {
        return workoutRepository.findByUser(user);  // Assuming you have this method in your repository
    }

}

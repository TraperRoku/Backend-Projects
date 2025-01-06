package com.TraperRoku.backend.controller;


import com.TraperRoku.backend.entities.Exercise;
import com.TraperRoku.backend.repository.ExerciseRepository;
import com.TraperRoku.backend.service.ExerciseService;
import com.TraperRoku.backend.service.WorkoutService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
   public WorkoutService workoutService;

    @Autowired
    public ExerciseRepository exerciseRepository;


    @Autowired
    private ExerciseService exerciseService;



    @PostMapping("/{workoutId}")
    public ResponseEntity<Exercise> addExercise(
            @PathVariable Long workoutId, // The workoutId comes from the URL path
            @RequestBody Exercise exercise // The exercise is still in the request body
    ) {
        if (workoutId == null) {
            throw new IllegalArgumentException("Workout ID is required");
        }

        // Call your service to add the exercise to the workout plan
        Exercise savedExercise = workoutService.addExerciseToWorkoutPlan(workoutId, exercise);
        return ResponseEntity.ok(savedExercise);
    }



    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/workout/{workoutId}")
    public List<Exercise> getExercisesByWorkoutId(@PathVariable Long workoutId) {
        return exerciseService.getExercisesByWorkoutId(workoutId);
    }



    @PutMapping("/{exerciseId}")
    public Exercise updateExerciseById(@PathVariable Long exerciseId, @RequestBody Exercise UpdatedExercise){
        Exercise exerciseById = exerciseService.findById(exerciseId);

        System.out.println("Received exerciseId: " + exerciseId);
        System.out.println("Received updatedExercise: " + UpdatedExercise);

        if(exerciseById == null){
            throw new RuntimeException("There is not that exerciseById "+ exerciseId);
        }

        exerciseById.setNameExercise(UpdatedExercise.getNameExercise());
        exerciseById.setSets(UpdatedExercise.getSets());
        exerciseById.setReps(UpdatedExercise.getReps());

        return exerciseService.saveExercise(exerciseById);
    }



    @DeleteMapping("/{exerciseId}")
    public void deleteExerciseById(@PathVariable Long exerciseId) {
        System.out.println("Received exerciseId for deletion: " + exerciseId);
        exerciseService.deleteById(exerciseId);
    }



}

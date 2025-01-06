package com.TraperRoku.backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id") //dałem to bo w w froncie mam jako id
    private Long exerciseId;

    private String nameExercise;

    private int sets;
    private int reps;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public Long getWorkoutId() {
        return workout != null ? workout.getId(): null;
    }

}

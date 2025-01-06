package com.TraperRoku.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_Workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nameWorkout;
    private LocalDate date;;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    @OneToMany(mappedBy = "workout",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "workout",cascade = CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;
}

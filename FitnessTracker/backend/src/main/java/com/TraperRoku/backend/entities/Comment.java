package com.TraperRoku.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "app_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String Text;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "workout_id")
    private Workout workout;
}

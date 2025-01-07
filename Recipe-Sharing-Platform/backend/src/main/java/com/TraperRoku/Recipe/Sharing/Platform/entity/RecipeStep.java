package com.TraperRoku.Recipe.Sharing.Platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stepNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;
}
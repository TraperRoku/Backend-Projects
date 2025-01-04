package com.TraperRoku.Recipe.Sharing.Platform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RecipeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private String path;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}

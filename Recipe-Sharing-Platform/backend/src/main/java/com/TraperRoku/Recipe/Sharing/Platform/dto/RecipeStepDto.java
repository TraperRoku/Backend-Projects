package com.TraperRoku.Recipe.Sharing.Platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeStepDto {
    private int stepNumber;
    private String description;

    private String imageUrl;
}
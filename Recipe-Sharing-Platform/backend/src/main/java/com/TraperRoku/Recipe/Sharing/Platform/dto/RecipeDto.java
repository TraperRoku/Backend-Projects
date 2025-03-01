package com.TraperRoku.Recipe.Sharing.Platform.dto;

import com.TraperRoku.Recipe.Sharing.Platform.Enum.DifficultyRecipe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private String chefLogin; // Zamiast zwracać cały obiekt Chef
    private List<String> imagePaths; // Lista ścieżek do obrazów
    private DifficultyRecipe difficulty;
    private List<RecipeStepDto> steps;
    private double time;
    private List<String> tags;
    private List<RecipeIngredientDto> ingredients;
}

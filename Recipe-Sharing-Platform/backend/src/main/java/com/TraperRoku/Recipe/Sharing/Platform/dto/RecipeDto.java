package com.TraperRoku.Recipe.Sharing.Platform.dto;

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
}

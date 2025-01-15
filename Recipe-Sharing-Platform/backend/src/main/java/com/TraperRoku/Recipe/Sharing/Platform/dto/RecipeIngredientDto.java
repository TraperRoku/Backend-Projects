package com.TraperRoku.Recipe.Sharing.Platform.dto;

import com.TraperRoku.Recipe.Sharing.Platform.Enum.IngredientsRecipeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredientDto {
    private String ingredient;
    private IngredientsRecipeEnum type;
    private int howMany;
}
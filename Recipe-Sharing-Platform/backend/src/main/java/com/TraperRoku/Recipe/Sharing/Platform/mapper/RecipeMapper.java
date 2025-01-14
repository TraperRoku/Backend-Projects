package com.TraperRoku.Recipe.Sharing.Platform.mapper;

import com.TraperRoku.Recipe.Sharing.Platform.dto.RecipeDto;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeImage;

import java.util.stream.Collectors;

public class RecipeMapper {
    public static RecipeDto toDto(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setChefLogin(recipe.getChef().getLogin());
        dto.setImagePaths(recipe.getImages().stream()
                .map(RecipeImage::getPath)
                .collect(Collectors.toList()));
        dto.setTime(recipe.getTime());
        dto.setDifficulty(recipe.getDifficulty());
        return dto;
    }
}
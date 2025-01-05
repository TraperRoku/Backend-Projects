package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    public Recipe findById(Long id){
        return recipeRepository.findById(id).orElseThrow(()-> new RuntimeException("There is not that Recipe"));
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllWithImagesAndChef();
    }
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }
    public Recipe updateRecipe(Long id, Recipe recipe) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    existingRecipe.setTitle(recipe.getTitle());
                    existingRecipe.setDescription(recipe.getDescription());
                    existingRecipe.setImages(recipe.getImages());
                    return recipeRepository.save(existingRecipe);
                })
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }
}
package com.TraperRoku.Recipe.Sharing.Platform.repository;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}

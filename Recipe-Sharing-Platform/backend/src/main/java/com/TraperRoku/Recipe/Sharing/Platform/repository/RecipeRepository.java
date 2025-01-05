package com.TraperRoku.Recipe.Sharing.Platform.repository;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.images LEFT JOIN FETCH r.chef")
    List<Recipe> findAllWithImagesAndChef();
}

package com.TraperRoku.Recipe.Sharing.Platform.repository;

import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepRepository extends JpaRepository<RecipeStep,Long> {
}

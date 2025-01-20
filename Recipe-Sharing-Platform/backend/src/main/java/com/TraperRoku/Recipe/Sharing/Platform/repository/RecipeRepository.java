package com.TraperRoku.Recipe.Sharing.Platform.repository;

import com.TraperRoku.Recipe.Sharing.Platform.Enum.DifficultyRecipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.images LEFT JOIN FETCH r.chef")
    List<Recipe> findAllWithImagesAndChef();

    @Query("SELECT DISTINCT r FROM Recipe r " +
            "LEFT JOIN r.chef c " +
            "LEFT JOIN r.tags t " +
            "WHERE (:keyword IS NULL OR " +
            "      LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "      LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:chef IS NULL OR LOWER(c.login) = LOWER(:chef)) " +
            "AND (:tag IS NULL OR :tag MEMBER OF r.tags) " +
            "AND (:difficulty IS NULL OR r.difficulty = :difficulty) " +
            "AND (:startDate IS NULL OR r.publicationDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.publicationDate <= :endDate)")
    List<Recipe> findRecipes(
            @Param("keyword") String keyword,
            @Param("chef") String chef,
            @Param("tag") String tag,
            @Param("difficulty") DifficultyRecipe difficulty,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

package com.TraperRoku.Recipe.Sharing.Platform.controller;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeImage;
import com.TraperRoku.Recipe.Sharing.Platform.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


    @RequestMapping("/api/recipes")
    @RequiredArgsConstructor
    @RestController
    public class RecipeController {
        private final RecipeService recipeService;
        private final ImageService imageService;
        private final ImageValidator imageValidator;
        private final JwtService jwtService;
        private final ChefService chefService;

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Recipe> createRecipe(
                @RequestPart("recipe") String recipeJson,
                @RequestPart("images") List<MultipartFile> images,
                HttpServletRequest request) throws IOException {

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Authorization header is missing or invalid.");
            }

            String token = authorizationHeader.substring(7);
            System.out.println("Received Token: " + token);
            String login = jwtService.extractLogin(token);

            Chef chef = chefService.findByLoginChef(login);

            // Validate images
            for (MultipartFile image : images) {
                imageValidator.validateImage(image);
            }

            ObjectMapper mapper = new ObjectMapper();
            Recipe recipe = mapper.readValue(recipeJson, Recipe.class);
            recipe.setChef(chef);

            Recipe savedRecipe = recipeService.createRecipe(recipe);
            List<RecipeImage> recipeImages = new ArrayList<>();

            for (MultipartFile image : images) {
                try {
                    RecipeImage recipeImage = imageService.saveImage(image, savedRecipe);
                    recipeImages.add(recipeImage);
                } catch (IOException e) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Failed to process image: " + image.getOriginalFilename(),
                            e
                    );
                }
            }

            savedRecipe.setImages(recipeImages);
            return ResponseEntity.ok(recipeService.updateRecipe(savedRecipe.getId(), savedRecipe));
        }


    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }
}
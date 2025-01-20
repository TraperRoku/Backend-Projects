package com.TraperRoku.Recipe.Sharing.Platform.controller;

import com.TraperRoku.Recipe.Sharing.Platform.Enum.DifficultyRecipe;
import com.TraperRoku.Recipe.Sharing.Platform.dto.RecipeDto;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Chef;
import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeImage;
import com.TraperRoku.Recipe.Sharing.Platform.mapper.RecipeMapper;
import com.TraperRoku.Recipe.Sharing.Platform.repository.RecipeRepository;
import com.TraperRoku.Recipe.Sharing.Platform.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequestMapping("/api/recipes")
    @RequiredArgsConstructor
    @RestController
    public class RecipeController {
        private final RecipeService recipeService;
        private final ImageService imageService;
        private final ImageValidator imageValidator;
        private final JwtService jwtService;
        private final ChefService chefService;
        private final RecipeRepository recipeRepository;

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
            recipe.setPublicationDate(LocalDate.now());

            if (recipe.getSteps() != null) {
            recipe.getSteps().forEach(step -> step.setRecipe(recipe));
            }



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




    @GetMapping("/uploads/images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws MalformedURLException {
        Path imagePath = Paths.get("uploads/images").resolve(fileName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not zfound: " + fileName);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // lub MediaType.IMAGE_PNG, zależnie od formatu
                .body(resource);
    }

    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeService.findAll().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }



    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/find")
    public ResponseEntity<List<RecipeDto>> findRecipes(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String chef,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) DifficultyRecipe difficulty,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Recipe> recipes = recipeRepository.findRecipes(keyword, chef, tag, difficulty, startDate, endDate);
        List<RecipeDto> recipeDtos = recipes.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recipeDtos);
    }

    // Add this method to support search by difficulty
    @GetMapping("/difficulties")
    public ResponseEntity<DifficultyRecipe[]> getDifficulties() {
        return ResponseEntity.ok(DifficultyRecipe.values());
    }

    // Add this method to get all available tags
    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        List<String> allTags = recipeService.findAll().stream()
                .flatMap(recipe -> recipe.getTags().stream())
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(allTags);
    }

    @GetMapping("/chefs")
    public ResponseEntity<List<String>> getAllChefs() {
        List<String> chefLogins = chefService.getAllChefs();
        return ResponseEntity.ok(chefLogins);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }else {
            recipeService.deleteRecipe(id);
        }
        return  ResponseEntity.ok(HttpStatus.OK);
    }
}
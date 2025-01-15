package com.TraperRoku.Recipe.Sharing.Platform.controller;

import com.TraperRoku.Recipe.Sharing.Platform.service.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/recipes/steps")
public class RecipeStepController {
    @Autowired
    private  RecipeStepService recipeStepService;

    @PostMapping("/{stepId}/upload-image")
    public ResponseEntity<String> uploadStepImage(
            @PathVariable Long stepId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            String imageUrl = recipeStepService.saveStepImage(stepId, image);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}
package com.TraperRoku.Recipe.Sharing.Platform.controller;

import com.TraperRoku.Recipe.Sharing.Platform.dto.RecipeStepDto;
import com.TraperRoku.Recipe.Sharing.Platform.service.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/recipes/steps")
public class RecipeStepController {
    @Autowired
    private  RecipeStepService recipeStepService;

    @PostMapping("/{stepNumber}/upload-image")
    public ResponseEntity<String> uploadStepImage(
            @PathVariable Long stepNumber,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            String imageUrl = recipeStepService.saveStepImage(stepNumber, image);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image");
        }
    }

    @PostMapping
    public ResponseEntity<RecipeStepDto> createStep (@RequestBody RecipeStepDto stepDto){
        RecipeStepDto savedStep = recipeStepService.saveStep(stepDto);
        return ResponseEntity.ok(savedStep);
    }


    @GetMapping("/uploads/step-images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws MalformedURLException {
        Path imagePath = Paths.get("uploads/step-images").resolve(fileName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not zfound: " + fileName);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
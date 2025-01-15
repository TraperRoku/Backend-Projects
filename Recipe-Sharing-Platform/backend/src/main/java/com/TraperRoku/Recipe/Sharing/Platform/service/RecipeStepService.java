package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeStep;
import com.TraperRoku.Recipe.Sharing.Platform.repository.RecipeStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class RecipeStepService {

    @Autowired
    private  RecipeStepRepository recipeStepRepository;

    public String saveStepImage(Long stepId, MultipartFile image) throws IOException {
        RecipeStep step = recipeStepRepository.findById(stepId)
                .orElseThrow(() -> new RuntimeException("Step not found"));

        // Save the image file to disk or cloud storage
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get("uploads/step-images/" + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, image.getBytes());

        // Update the RecipeStep entity with the image URL
        step.setImageUrl("/uploads/step-images/" + fileName);
        recipeStepRepository.save(step);

        return step.getImageUrl();
    }
}

package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeImage;
import com.TraperRoku.Recipe.Sharing.Platform.repository.RecipeImageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String uploadDir = "uploads/images/";
    private final RecipeImageRepository recipeImageRepository;

    // Standard dimensions for recipe images
    private static final int TARGET_WIDTH = 800;
    private static final int TARGET_HEIGHT = 600;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public RecipeImage saveImage(MultipartFile file, Recipe recipe) throws IOException {
        // Process and resize the image
        byte[] resizedImageBytes = resizeImage(file);

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationPath = Paths.get(uploadDir + fileName);

        // Save the processed image
        Files.write(destinationPath, resizedImageBytes);

        // Create and save RecipeImage entity
        RecipeImage recipeImage = new RecipeImage();
        recipeImage.setFileName(fileName);
        recipeImage.setContentType(file.getContentType());
        recipeImage.setPath(destinationPath.toString());
        recipeImage.setRecipe(recipe);

        return recipeImageRepository.save(recipeImage);


/*@Service
@RequiredArgsConstructor
public class ImageService {
    private static final int TARGET_WIDTH = 800;
    private static final int TARGET_HEIGHT = 600;
    private final String uploadDir = "uploads/images/";  // Use forward slashes
    private final RecipeImageRepository recipeImageRepository;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public RecipeImage saveImage(MultipartFile file, Recipe recipe) throws IOException {
        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Normalize path with forward slashes
        String normalizedPath = uploadDir + fileName.replace("\\", "/");
        Path destinationPath = Paths.get(normalizedPath);

        // Save the file
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        // Create and save RecipeImage entity
        RecipeImage recipeImage = new RecipeImage();
        recipeImage.setFileName(fileName);
        recipeImage.setContentType(file.getContentType());
        recipeImage.setPath(normalizedPath);
        recipeImage.setRecipe(recipe);

        return recipeImageRepository.save(recipeImage);*/
    }


    private byte[] resizeImage(MultipartFile file) throws IOException {
        // Read the original image
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Create new BufferedImage with target dimensions
        Image resultingImage = originalImage.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Draw the resized image
        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(resultingImage, 0, 0, null);
        graphics2D.dispose();

        // Convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(outputImage, getImageFormat(file.getOriginalFilename()), baos);
        return baos.toByteArray();
    }

    private String getImageFormat(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.toLowerCase();
    }

    // Add utility method to get image dimensions
    public record ImageDimensions(int width, int height) {}

    public ImageDimensions getImageDimensions(MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        return new ImageDimensions(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public boolean verifyImageExists(String fileName) {
        Path imagePath = Paths.get(uploadDir + fileName);
        return Files.exists(imagePath);
    }

    public String getFullImagePath(String fileName) {
        return Paths.get(uploadDir + fileName).toAbsolutePath().toString();
    }


}
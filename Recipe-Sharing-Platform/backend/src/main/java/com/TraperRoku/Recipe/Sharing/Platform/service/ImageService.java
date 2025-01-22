package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.TraperRoku.Recipe.Sharing.Platform.entity.Recipe;
import com.TraperRoku.Recipe.Sharing.Platform.entity.RecipeImage;
import com.TraperRoku.Recipe.Sharing.Platform.repository.RecipeImageRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final String uploadDir = "uploads/images/";
    private final RecipeImageRepository recipeImageRepository;

    // Standard dimensions for recipe images
    private static final int TARGET_WIDTH = 800;
    private static final int TARGET_HEIGHT = 600;

    // Allowed image formats
    private static final Set<String> ALLOWED_FORMATS = new HashSet<>(Arrays.asList(
            "jpg", "jpeg", "png", "gif"
    ));

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public RecipeImage saveImage(MultipartFile file, Recipe recipe) throws IOException {
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }

        // Validate file format
        String format = getImageFormat(file.getOriginalFilename());
        if (!ALLOWED_FORMATS.contains(format.toLowerCase())) {
            throw new IllegalArgumentException("Unsupported image format. Allowed formats: " +
                    String.join(", ", ALLOWED_FORMATS));
        }

        // Process and resize the image
        byte[] resizedImageBytes = resizeImage(file);
        if (resizedImageBytes == null) {
            throw new IOException("Failed to process image");
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "." + format;
        Path destinationPath = Paths.get(uploadDir + fileName);

        // Save the processed image
        try {
            Files.write(destinationPath, resizedImageBytes);
        } catch (IOException e) {
            throw new IOException("Failed to save image to filesystem", e);
        }

        // Create and save RecipeImage entity
        RecipeImage recipeImage = new RecipeImage();
        recipeImage.setFileName(fileName);
        recipeImage.setContentType(file.getContentType());
        recipeImage.setPath(destinationPath.toString());
        recipeImage.setRecipe(recipe);

        try {
            return recipeImageRepository.save(recipeImage);
        } catch (Exception e) {
            // Clean up the file if database save fails
            Files.deleteIfExists(destinationPath);
            throw new RuntimeException("Failed to save image metadata to database", e);
        }
    }

    private byte[] resizeImage(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        if (originalImage == null) {
            throw new IOException("Failed to read image file");
        }

        // Determine if the image has transparency
        boolean hasAlpha = originalImage.getColorModel().hasAlpha();

        // Create new BufferedImage with appropriate type
        BufferedImage outputImage = new BufferedImage(
                TARGET_WIDTH,
                TARGET_HEIGHT,
                hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
        );

        // Use better quality hints
        Graphics2D graphics2D = outputImage.createGraphics();
        try {
            graphics2D.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            graphics2D.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );
            graphics2D.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            graphics2D.drawImage(originalImage, 0, 0, TARGET_WIDTH, TARGET_HEIGHT, null);
        } finally {
            graphics2D.dispose();
        }

        // Convert to byte array while preserving format
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String format = getImageFormat(file.getOriginalFilename());

        // For PNG, ensure we use a writer that can handle transparency
        if (format.equalsIgnoreCase("png")) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
            if (writers.hasNext()) {
                ImageWriter writer = writers.next();
                ImageWriteParam writeParam = writer.getDefaultWriteParam();
                ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
                writer.setOutput(ios);
                writer.write(null, new IIOImage(outputImage, null, null), writeParam);
                ios.close();
                writer.dispose();
            }
        } else {
            ImageIO.write(outputImage, format, baos);
        }

        return baos.toByteArray();
    }

    private String getImageFormat(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("Invalid filename");
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
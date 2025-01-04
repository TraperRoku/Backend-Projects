package com.TraperRoku.Recipe.Sharing.Platform.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class ImageValidator {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_FORMATS = Arrays.asList("image/jpeg", "image/png", "image/gif");

    public void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 5MB");
        }

        if (!ALLOWED_FORMATS.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file format. Allowed formats are JPEG, PNG, and GIF");
        }
    }
}
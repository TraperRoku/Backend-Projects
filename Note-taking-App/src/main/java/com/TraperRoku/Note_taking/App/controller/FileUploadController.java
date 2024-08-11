package com.TraperRoku.Note_taking.App.controller;

import com.TraperRoku.Note_taking.App.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private FileStorageService fileStorageService;

    @Autowired
    public FileUploadController (FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String filename = fileStorageService.StoreFile(file);
            return ResponseEntity.ok("File uploaded "+filename);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: "+ e.getMessage());
        }

    }



}

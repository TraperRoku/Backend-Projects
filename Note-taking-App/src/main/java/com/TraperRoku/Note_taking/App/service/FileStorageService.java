package com.TraperRoku.Note_taking.App.service;

import com.TraperRoku.Note_taking.App.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path rootLocation = Paths.get("uploaded-files");

    public FileStorageService(){
        try{
            Files.createDirectories(rootLocation);
        }catch(IOException e){
            throw new RuntimeException("Could not initialize storage",e);
        }
    }

    public String StoreFile(MultipartFile file) throws IOException{
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString()+"."+extension;
        Path destinationFile = rootLocation.resolve(
                Paths.get(fileName)
        ).normalize().toAbsolutePath();
     if(!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
         throw new StorageException("Cannot store file outside current directory.");
     }
     try(InputStream inputStream = file.getInputStream()){
         Files.copy(inputStream,destinationFile);
         return fileName;
     } catch (IOException e){
         throw new IOException("Failed to store a file "+ fileName,e);
     }
    }
}

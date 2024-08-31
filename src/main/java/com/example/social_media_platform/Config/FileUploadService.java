package com.example.social_media_platform.Config;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//this service is to get the file and save it to the file system
//each file is saved with a unique name

@Service
public class FileUploadService {

    private final String UPLOAD_DIR = "C:/Users/DELL/VS-Projects/Social_Media_Frontend/public/uploads";

    public String saveFile(MultipartFile file) throws IOException {
        // Ensure upload directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save file to the file system
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        // Return the URL or path to the saved file
        return filename;
    }
}

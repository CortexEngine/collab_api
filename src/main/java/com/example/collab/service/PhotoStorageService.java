package com.example.collab.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.collab.exception.business.BadRequestException;
import com.example.collab.exception.resource.NotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class PhotoStorageService {

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif", "webp");

    @Value("${app.upload.path:uploads/photos}")
    private String uploadPath;

    private Path rootLocation;

    @PostConstruct
    public void init() {

        this.rootLocation = Paths.get(uploadPath).toAbsolutePath().normalize();

        try {

            Files.createDirectories(rootLocation);

        } catch (IOException e) {

            throw new RuntimeException("Could not create upload directory: " + rootLocation, e);

        }

    }

    public String savePhoto(MultipartFile file) {

        if (file == null || file.isEmpty()) {

            throw new BadRequestException("Photo file must not be empty");

        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {

            throw new BadRequestException("Photo file must have a name");

        }

        String extension = getExtension(originalFilename).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {

            throw new BadRequestException("Invalid file type: " + extension + ". Allowed: " + ALLOWED_EXTENSIONS);

        }

        String newFilename = UUID.randomUUID() + "." + extension;

        Path destination = rootLocation.resolve(newFilename).normalize();

        if (!destination.startsWith(rootLocation)) {

            throw new BadRequestException("Invalid file path");

        }

        try {

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            throw new RuntimeException("Failed to store file: " + newFilename, e);

        }

        return newFilename;

    }

    public Resource loadPhoto(String path) {

        try {

            Path file = rootLocation.resolve(path).normalize();

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {

                return resource;

            }

            throw new NotFoundException("Photo not found: " + path);

        } catch (MalformedURLException e) {

            throw new NotFoundException("Photo not found: " + path);

        }

    }

    public void deletePhoto(String path) {

        if (path == null || path.isBlank()) {

            return;

        }

        try {

            Path file = rootLocation.resolve(path).normalize();

            Files.deleteIfExists(file);

        } catch (IOException e) {

            throw new RuntimeException("Failed to delete file: " + path, e);

        }

    }

    private String getExtension(String filename) {

        int dotIndex = filename.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex == filename.length() - 1) {

            throw new BadRequestException("File must have an extension");

        }

        return filename.substring(dotIndex + 1);

    }

}

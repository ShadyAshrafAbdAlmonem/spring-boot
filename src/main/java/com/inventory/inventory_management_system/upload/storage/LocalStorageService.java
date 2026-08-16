package com.inventory.inventory_management_system.upload.storage;

import com.inventory.inventory_management_system.upload.config.StorageProperties;
import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import com.inventory.inventory_management_system.upload.exception.FileUploadException;
import com.inventory.inventory_management_system.upload.exception.InvalidFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service("localStorageService")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class LocalStorageService implements StorageService {

    private final StorageProperties storageProperties;

    @Override
    public UploadResponse store(MultipartFile file, String destinationFileName, String folder) {
        try {
            Path rootLocation = Paths.get(storageProperties.getLocal().getUploadDir(), folder);
            Files.createDirectories(rootLocation);

            Path destinationFile = rootLocation.resolve(Paths.get(destinationFileName)).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new InvalidFileException("Cannot store file outside current directory path.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return UploadResponse.builder()
                    .fileName(destinationFileName)
                    .fileUrl("/api/v1/uploads/files/" + folder + "/" + destinationFileName)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .storageProvider(getProviderName())
                    .build();

        } catch (Exception e) {
            throw new FileUploadException("Failed to store file locally", e);
        }
    }

    @Override
    public Resource loadAsResource(String fileName, String folder) {
        try {
            Path filePath = Paths.get(storageProperties.getLocal().getUploadDir(), folder, fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new InvalidFileException("Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new InvalidFileException("Could not read file: " + fileName);
        }
    }

    @Override
    public boolean delete(String fileName, String folder) {
        try {
            Path filePath = Paths.get(storageProperties.getLocal().getUploadDir(), folder, fileName);
            return Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new FileUploadException("Could not delete local file: " + fileName, e);
        }
    }

    @Override
    public String getProviderName() {
        return "LOCAL";
    }
}
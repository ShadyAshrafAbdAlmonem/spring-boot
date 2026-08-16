package com.inventory.inventory_management_system.upload.validator;

import com.inventory.inventory_management_system.upload.config.StorageProperties;
import com.inventory.inventory_management_system.upload.exception.FileTooLargeException;
import com.inventory.inventory_management_system.upload.exception.InvalidFileException;
import com.inventory.inventory_management_system.upload.exception.UnsupportedFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileValidator {

    private final StorageProperties storageProperties;

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("Cannot upload an empty or null file.");
        }

        if (file.getSize() > storageProperties.getMaxFileSize()) {
            throw new FileTooLargeException("File size exceeds maximum allowed limit of " +
                    (storageProperties.getMaxFileSize() / (1024 * 1024)) + " MB.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !storageProperties.getAllowedContentTypes().contains(contentType)) {
            throw new UnsupportedFileTypeException("File type '" + contentType + "' is not supported.");
        }
    }
}
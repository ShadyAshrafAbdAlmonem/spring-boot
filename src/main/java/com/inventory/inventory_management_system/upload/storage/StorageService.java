package com.inventory.inventory_management_system.upload.storage;

import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    UploadResponse store(MultipartFile file, String destinationFileName, String folder);
    Resource loadAsResource(String fileName, String folder);
    boolean delete(String fileName, String folder);
    String getProviderName();
}
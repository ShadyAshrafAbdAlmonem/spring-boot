package com.inventory.inventory_management_system.upload.storage;

import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import com.inventory.inventory_management_system.upload.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("minioStorageService")
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    @Override
    public UploadResponse store(MultipartFile file, String destinationFileName, String folder) {
        // Integrate MinioClient here when ready
        throw new FileUploadException("MinIO storage driver is not yet configured.");
    }

    @Override
    public Resource loadAsResource(String fileName, String folder) {
        throw new FileUploadException("MinIO storage driver is not yet configured.");
    }

    @Override
    public boolean delete(String fileName, String folder) {
        throw new FileUploadException("MinIO storage driver is not yet configured.");
    }

    @Override
    public String getProviderName() {
        return "MINIO";
    }
}
package com.inventory.inventory_management_system.upload.service.impl;

import com.inventory.inventory_management_system.upload.dto.response.DeleteFileResponse;
import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import com.inventory.inventory_management_system.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Override
    public UploadResponse uploadSingleFile(MultipartFile file, String folder) {
        // Placeholder implementation
        return UploadResponse.builder().fileName(file.getOriginalFilename()).fileUrl("/uploads/" + folder + "/" + file.getOriginalFilename()).build();
    }

    @Override
    public List<UploadResponse> uploadMultipleFiles(List<MultipartFile> files, String folder) {
        return files.stream()
                .map(file -> uploadSingleFile(file, folder))
                .collect(Collectors.toList());
    }

    @Override
    public Resource getFileAsResource(String fileName, String folder) {
        // Placeholder implementation
        return null;
    }

    @Override
    public DeleteFileResponse deleteFile(String fileName, String folder) {
        // Placeholder implementation
        return DeleteFileResponse.builder().message("File deleted successfully.").fileName(fileName).build();
    }
}
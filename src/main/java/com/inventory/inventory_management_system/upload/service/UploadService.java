package com.inventory.inventory_management_system.upload.service;

import com.inventory.inventory_management_system.upload.dto.response.DeleteFileResponse;
import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    UploadResponse uploadSingleFile(MultipartFile file, String folder);
    List<UploadResponse> uploadMultipleFiles(List<MultipartFile> files, String folder);
    Resource getFileAsResource(String fileName, String folder);
    DeleteFileResponse deleteFile(String fileName, String folder);
}
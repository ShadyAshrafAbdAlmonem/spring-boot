package com.inventory.inventory_management_system.upload.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {
    private MultipartFile file;
    private String folder = "general";
}
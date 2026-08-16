package com.inventory.inventory_management_system.upload.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MultiUploadRequest {
    private List<MultipartFile> files;
    private String folder = "general";
}

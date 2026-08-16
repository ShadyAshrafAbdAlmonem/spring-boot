package com.inventory.inventory_management_system.upload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private String fileName;
    private String fileUrl;
    private String contentType;
    private long size;
    private String storageProvider;
}
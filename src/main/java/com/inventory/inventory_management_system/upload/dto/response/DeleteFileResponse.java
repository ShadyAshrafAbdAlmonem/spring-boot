package com.inventory.inventory_management_system.upload.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteFileResponse {
    private String message;
    private String fileName;
}
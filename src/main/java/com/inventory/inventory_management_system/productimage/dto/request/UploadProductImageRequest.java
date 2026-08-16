package com.inventory.inventory_management_system.productimage.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadProductImageRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "File is required")
    private MultipartFile file;

    private Boolean isPrimary = false;
}

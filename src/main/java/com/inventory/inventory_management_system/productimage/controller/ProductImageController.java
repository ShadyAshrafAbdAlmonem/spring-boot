package com.inventory.inventory_management_system.productimage.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.productimage.dto.request.UploadProductImageRequest;
import com.inventory.inventory_management_system.productimage.dto.response.ProductImageResponse;
import com.inventory.inventory_management_system.productimage.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/product-images")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProductImageResponse>> uploadImage(@Valid @ModelAttribute UploadProductImageRequest request) {
        ProductImageResponse response = productImageService.uploadImage(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Image uploaded successfully"));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getImagesByProductId(@PathVariable @NonNull Long productId) {
        return ResponseEntity.ok(ApiResponse.success(productImageService.getImagesByProductId(productId)));
    }

    @PatchMapping("/{imageId}/set-primary")
    public ResponseEntity<ApiResponse<ProductImageResponse>> setPrimaryImage(@PathVariable @NonNull Long imageId) {
        return ResponseEntity.ok(ApiResponse.success(productImageService.setPrimaryImage(imageId), "Set primary image successfully"));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable @NonNull Long imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.ok(ApiResponse.success(null, "Image deleted successfully"));
    }
}
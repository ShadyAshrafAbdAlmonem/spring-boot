package com.inventory.inventory_management_system.upload.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.upload.dto.response.DeleteFileResponse;
import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import com.inventory.inventory_management_system.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<UploadResponse>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "general") String folder) {

        UploadResponse response = uploadService.uploadSingleFile(file, folder);
        return ResponseEntity.ok(ApiResponse.success(response, "File uploaded successfully"));
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<UploadResponse>>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(defaultValue = "general") String folder) {

        List<UploadResponse> responses = uploadService.uploadMultipleFiles(files, folder);
        return ResponseEntity.ok(ApiResponse.success(responses, "Files uploaded successfully"));
    }

    @GetMapping("/files/{folder}/{fileName:.+}")
    public ResponseEntity<Resource> getFile(
            @PathVariable String folder,
            @PathVariable String fileName) {

        Resource resource = uploadService.getFileAsResource(fileName, folder);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/files/{folder}/{fileName:.+}")
    public ResponseEntity<ApiResponse<DeleteFileResponse>> deleteFile(
            @PathVariable String folder,
            @PathVariable String fileName) {

        DeleteFileResponse response = uploadService.deleteFile(fileName, folder);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
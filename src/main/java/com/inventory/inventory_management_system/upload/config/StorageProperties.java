package com.inventory.inventory_management_system.upload.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private String activeStrategy = "local"; // local, minio, s3
    private long maxFileSize = 10485760; // 10MB in bytes
    private List<String> allowedContentTypes = List.of("image/jpeg", "image/png", "image/webp", "application/pdf");

    private Local local = new Local();
    private Minio minio = new Minio();
    private S3 s3 = new S3();

    @Data
    public static class Local {
        private String uploadDir = "uploads";
    }

    @Data
    public static class Minio {
        private String endpoint = "http://localhost:9000";
        private String accessKey = "minioadmin";
        private String secretKey = "minioadmin";
        private String bucket = "inventory-bucket";
    }

    @Data
    public static class S3 {
        private String region = "us-east-1";
        private String accessKey;
        private String secretKey;
        private String bucket = "inventory-bucket";
    }
}

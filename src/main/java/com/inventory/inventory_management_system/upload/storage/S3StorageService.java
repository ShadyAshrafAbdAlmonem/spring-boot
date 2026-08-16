package com.inventory.inventory_management_system.upload.storage;

import com.inventory.inventory_management_system.upload.config.StorageProperties;
import com.inventory.inventory_management_system.upload.dto.response.UploadResponse;
import com.inventory.inventory_management_system.upload.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;

@Service("s3StorageService")
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final StorageProperties storageProperties;

    /**
     * Lazily builds and returns an S3Client using credentials from StorageProperties.
     * The client is created on-demand to avoid startup failures when S3 is not configured.
     */
    private S3Client getS3Client() {
        StorageProperties.S3 s3Config = storageProperties.getS3();
        if (s3Config.getAccessKey() == null || s3Config.getSecretKey() == null) {
            throw new FileUploadException("AWS S3 credentials are not configured");
        }

        return S3Client.builder()
                .region(Region.of(s3Config.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(s3Config.getAccessKey(), s3Config.getSecretKey())
                ))
                .build();
    }

    @Override
    public UploadResponse store(MultipartFile file, String destinationFileName, String folder) {
        try {
            S3Client s3 = getS3Client();
            StorageProperties.S3 s3Config = storageProperties.getS3();
            String bucket = s3Config.getBucket();
            String key = folder != null && !folder.isEmpty() ? folder + "/" + destinationFileName : destinationFileName;

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            String fileDownloadUri = s3.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(key).build()).toString();

            return UploadResponse.builder()
                    .fileName(destinationFileName)
                    .fileUrl(fileDownloadUri)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .storageProvider("S3")
                    .build();

        } catch (IOException | S3Exception e) {
            throw new FileUploadException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource loadAsResource(String fileName, String folder) {
        try {
            S3Client s3 = getS3Client();
            StorageProperties.S3 s3Config = storageProperties.getS3();
            String bucket = s3Config.getBucket();
            String key = folder != null && !folder.isEmpty() ? folder + "/" + fileName : fileName;

            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3.getObjectAsBytes(getRequest);

            // Build a file:// URI pointing to the object URL for Resource loading
            URI objectUri = s3.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(key).build()).toURI();
            return new UrlResource(objectUri);

        } catch (Exception e) {
            throw new FileUploadException("Failed to load file from S3: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(String fileName, String folder) {
        try {
            S3Client s3 = getS3Client();
            StorageProperties.S3 s3Config = storageProperties.getS3();
            String bucket = s3Config.getBucket();
            String key = folder != null && !folder.isEmpty() ? folder + "/" + fileName : fileName;

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3.deleteObject(deleteRequest);
            return true;

        } catch (S3Exception e) {
            throw new FileUploadException("Failed to delete file from S3: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an object from S3 Bucket given its full URL.
     *
     * <p>The bucket is inferred from the configured S3 bucket and the key
     * is extracted from the URL path. This is useful when deleting files
     * that were stored via their publicly accessible URL.</p>
     *
     * @param imageUrl the S3 object URL to delete
     */
    public void deleteFromS3(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            log.warn("Attempted to delete a null or empty image URL from S3");
            return;
        }

        try {
            S3Client s3 = getS3Client();
            StorageProperties.S3 s3Config = storageProperties.getS3();
            String bucket = s3Config.getBucket();

            // Extract the key from the URL
            // The S3 URL format is: https://<bucket>.s3.<region>.amazonaws.com/<key>
            // or https://s3.<region>.amazonaws.com/<bucket>/<key>
            String key = extractKeyFromUrl(imageUrl);
            if (key == null || key.isEmpty()) {
                log.warn("Could not extract key from URL: {}", imageUrl);
                return;
            }

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3.deleteObject(deleteRequest);
            log.info("Successfully deleted object with key '{}' from bucket '{}'", key, bucket);

        } catch (S3Exception e) {
            throw new FileUploadException("Failed to delete file from S3: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the S3 object key from a full S3 URL.
     */
    private String extractKeyFromUrl(String imageUrl) {
        try {
            URI uri = URI.create(imageUrl);
            String path = uri.getPath();
            // Remove leading slash
            if (path.startsWith("/")) {
                path = path.substring(1);
            }

            StorageProperties.S3 s3Config = storageProperties.getS3();
            String bucket = s3Config.getBucket();

            // If the first segment of the path is the bucket name (path-style), remove it
            if (path.startsWith(bucket + "/")) {
                path = path.substring(bucket.length() + 1);
            }

            return path;
        } catch (Exception e) {
            log.error("Failed to parse S3 URL: {}", imageUrl, e);
            return null;
        }
    }

    @Override
    public String getProviderName() {
        return "S3";
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(S3StorageService.class);
}

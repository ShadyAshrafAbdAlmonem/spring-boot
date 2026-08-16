package com.inventory.inventory_management_system.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for file operations.
 */
public class FileUtil {

    private static final long DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");
    private static final List<String> ALLOWED_DOCUMENT_EXTENSIONS = Arrays.asList("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt");
    private static final List<String> ALLOWED_ARCHIVE_EXTENSIONS = Arrays.asList("zip", "rar", "7z", "tar", "gz");

    /**
     * Generate unique file name
     */
    public static String generateUniqueFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        return UUID.randomUUID().toString() + (extension != null ? "." + extension : "");
    }

    /**
     * Get file extension from filename
     */
    public static String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return null;
        }
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1 || lastIndexOf == filename.length() - 1) {
            return null;
        }
        return filename.substring(lastIndexOf + 1).toLowerCase();
    }

    /**
     * Get file name without extension
     */
    public static String getFileNameWithoutExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return null;
        }
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(0, lastIndexOf);
    }

    /**
     * Validate file size
     */
    public static boolean isValidFileSize(long fileSize, long maxSize) {
        return fileSize > 0 && fileSize <= maxSize;
    }

    /**
     * Validate file size with default max size
     */
    public static boolean isValidFileSize(long fileSize) {
        return isValidFileSize(fileSize, DEFAULT_MAX_FILE_SIZE);
    }

    /**
     * Validate file extension
     */
    public static boolean isValidExtension(String filename, List<String> allowedExtensions) {
        String extension = getFileExtension(filename);
        return extension != null && allowedExtensions.contains(extension.toLowerCase());
    }

    /**
     * Check if file is an image
     */
    public static boolean isImage(String filename) {
        return isValidExtension(filename, ALLOWED_IMAGE_EXTENSIONS);
    }

    /**
     * Check if file is a document
     */
    public static boolean isDocument(String filename) {
        return isValidExtension(filename, ALLOWED_DOCUMENT_EXTENSIONS);
    }

    /**
     * Check if file is an archive
     */
    public static boolean isArchive(String filename) {
        return isValidExtension(filename, ALLOWED_ARCHIVE_EXTENSIONS);
    }

    /**
     * Get MIME type from file extension
     */
    public static String getMimeType(String filename) {
        String extension = getFileExtension(filename);
        if (extension == null) {
            return "application/octet-stream";
        }
        return URLConnection.guessContentTypeFromName("file." + extension);
    }

    /**
     * Read file to byte array
     */
    public static byte[] readFileToByteArray(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Read file to byte array
     */
    public static byte[] readFileToByteArray(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    /**
     * Write byte array to file
     */
    public static void writeByteArrayToFile(byte[] bytes, File file) throws IOException {
        Files.write(file.toPath(), bytes);
    }

    /**
     * Write byte array to file
     */
    public static void writeByteArrayToFile(byte[] bytes, Path path) throws IOException {
        Files.write(path, bytes);
    }

    /**
     * Create directory if not exists
     */
    public static void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Delete file if exists
     */
    public static boolean deleteFileIfExists(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Copy file
     */
    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Move file
     */
    public static void moveFile(String sourcePath, String destinationPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path destination = Paths.get(destinationPath);
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Get file size in bytes
     */
    public static long getFileSize(String filePath) {
        try {
            return Files.size(Paths.get(filePath));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * Get file size from MultipartFile
     */
    public static long getFileSize(MultipartFile file) {
        return file != null ? file.getSize() : -1;
    }

    /**
     * Convert MultipartFile to byte array
     */
    public static byte[] multipartFileToBytes(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IOException("File is empty or null");
        }
        return file.getBytes();
    }

    /**
     * Convert byte array to MultipartFile (requires additional implementation)
     * This is a placeholder - actual implementation depends on the framework
     */
    public static MultipartFile bytesToMultipartFile(byte[] bytes, String filename) {
        // Implementation depends on the specific MultipartFile implementation
        // This is a simplified version
        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return getMimeType(filename);
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes != null ? bytes.length : 0;
            }

            @Override
            public byte[] getBytes() {
                return bytes;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException {
                writeByteArrayToFile(bytes, dest);
            }
        };
    }

    /**
     * Format file size to human-readable string
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 0) {
            return "N/A";
        }
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }

    /**
     * Check if file exists
     */
    public static boolean exists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    /**
     * List files in directory
     */
    public static List<Path> listFiles(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        if (!Files.isDirectory(dir)) {
            throw new IOException("Path is not a directory: " + directoryPath);
        }
        return Files.list(dir).toList();
    }

    /**
     * List all files recursively in directory
     */
    public static List<Path> listFilesRecursively(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);
        if (!Files.isDirectory(dir)) {
            throw new IOException("Path is not a directory: " + directoryPath);
        }
        return Files.walk(dir).toList();
    }
}

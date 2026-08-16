package com.inventory.inventory_management_system.upload.util;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ImageUtil {

    private static final Set<String> IMAGE_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"
    );

    public static boolean isImage(String contentType) {
        return contentType != null && IMAGE_CONTENT_TYPES.contains(contentType.toLowerCase());
    }
}
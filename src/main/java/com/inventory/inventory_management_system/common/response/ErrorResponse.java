package com.inventory.inventory_management_system.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Boolean success;
    private String message;
    private Integer statusCode;
    private Map<String, String> errors; // لإرجاع أخطاء الـ Validation إن وجدت
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
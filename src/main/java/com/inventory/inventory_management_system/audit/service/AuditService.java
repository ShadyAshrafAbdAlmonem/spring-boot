package com.inventory.inventory_management_system.audit.service;

import com.inventory.inventory_management_system.audit.entity.AuditLog;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AuditService {
    void logAction(@NonNull String action, @NonNull String entityName, @NonNull Long entityId, @NonNull String performedBy, String details);
    @NonNull List<AuditLog> getLogsForEntity(@NonNull String entityName, @NonNull Long entityId);
}
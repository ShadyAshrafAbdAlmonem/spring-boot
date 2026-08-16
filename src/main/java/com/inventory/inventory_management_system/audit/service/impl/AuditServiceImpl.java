package com.inventory.inventory_management_system.audit.service.impl;
import com.inventory.inventory_management_system.audit.entity.AuditLog;
import com.inventory.inventory_management_system.audit.repository.AuditLogRepository;
import com.inventory.inventory_management_system.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void logAction(@NonNull String action, @NonNull String entityName, @NonNull Long entityId, @NonNull String performedBy, String details) {
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .performedBy(performedBy)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        Objects.requireNonNull(auditLogRepository.save(auditLog), "Saved audit log must not be null");
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull List<AuditLog> getLogsForEntity(@NonNull String entityName, @NonNull Long entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId);
    }
}

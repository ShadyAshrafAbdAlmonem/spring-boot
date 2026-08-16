package com.inventory.inventory_management_system.activitylog.repository;

import com.inventory.inventory_management_system.activitylog.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>, JpaSpecificationExecutor<ActivityLog> {

    // Find logs by username
    List<ActivityLog> findByUsername(String username);

    // Find logs by username with pagination
    Page<ActivityLog> findByUsername(String username, Pageable pageable);

    // Find logs by action type
    List<ActivityLog> findByAction(String action);

    // Find logs by entity name and entity ID
    List<ActivityLog> findByEntityNameAndEntityId(String entityName, Long entityId);

    // Find logs by timestamp range
    List<ActivityLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find logs by username and timestamp range
    List<ActivityLog> findByUsernameAndTimestampBetween(String username, LocalDateTime start, LocalDateTime end);

    // Find logs by action and timestamp range
    List<ActivityLog> findByActionAndTimestampBetween(String action, LocalDateTime start, LocalDateTime end);

    // Find logs by IP address
    List<ActivityLog> findByIpAddress(String ipAddress);

    // Find recent logs (e.g., last N days)
    List<ActivityLog> findByTimestampAfter(LocalDateTime timestamp);
}

package com.inventory.inventory_management_system.audit.specification;

import com.inventory.inventory_management_system.audit.dto.request.AuditSearchRequest;
import com.inventory.inventory_management_system.audit.entity.AuditLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuditSpecification {

    public static Specification<AuditLog> filterLogs(AuditSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getAction() != null && !request.getAction().isBlank()) {
                predicates.add(cb.equal(root.get("action"), request.getAction()));
            }
            if (request.getEntityName() != null && !request.getEntityName().isBlank()) {
                predicates.add(cb.equal(root.get("entityName"), request.getEntityName()));
            }
            if (request.getEntityId() != null) {
                predicates.add(cb.equal(root.get("entityId"), request.getEntityId()));
            }
            if (request.getPerformedBy() != null && !request.getPerformedBy().isBlank()) {
                predicates.add(cb.equal(root.get("performedBy"), request.getPerformedBy()));
            }
            if (request.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), request.getStartDate().atStartOfDay()));
            }
            if (request.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), request.getEndDate().atTime(23, 59, 59)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
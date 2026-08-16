package com.inventory.inventory_management_system.activitylog.specification;

import com.inventory.inventory_management_system.activitylog.dto.request.ActivityLogFilterRequest;
import com.inventory.inventory_management_system.activitylog.entity.ActivityLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ActivityLogSpecification {

    public static @NonNull Specification<ActivityLog> filterLogs(@NonNull ActivityLogFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUsername() != null && !filter.getUsername().isBlank()) { // Assumes ActivityLogFilterRequest has @Getter
                predicates.add(cb.equal(root.get("username"), filter.getUsername())); 
            }
            if (filter.getAction() != null && !filter.getAction().isBlank()) { // Assumes ActivityLogFilterRequest has @Getter
                predicates.add(cb.equal(root.get("action"), filter.getAction())); 
            }
            if (filter.getStartDate() != null && filter.getEndDate() != null) { // Assumes ActivityLogFilterRequest has @Getter
                predicates.add(cb.between(root.get("timestamp"), filter.getStartDate(), filter.getEndDate())); 
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

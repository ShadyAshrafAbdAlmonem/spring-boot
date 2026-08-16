package com.inventory.inventory_management_system.warehouse.specification;

import com.inventory.inventory_management_system.warehouse.entity.Warehouse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WarehouseSpecification {

    public static @NonNull Specification<Warehouse> filterWarehouses(String search, Boolean active) { // search and active can be null
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.isBlank()) {
                String pattern = "%" + search.toLowerCase() + "%";
                Predicate nameLike = cb.like(cb.lower(root.get("name")), pattern);
                Predicate codeLike = cb.like(cb.lower(root.get("code")), pattern);
                Predicate locationLike = cb.like(cb.lower(root.get("location")), pattern);
                predicates.add(cb.or(nameLike, codeLike, locationLike));
            }

            if (active != null) {
                predicates.add(cb.equal(root.get("active"), active));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

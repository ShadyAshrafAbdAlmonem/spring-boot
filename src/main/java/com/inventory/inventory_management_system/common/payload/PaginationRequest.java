package com.inventory.inventory_management_system.common.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Base pagination request class with common pagination parameters.
 * Extend this class or use it directly for paginated API requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    private int page = DEFAULT_PAGE;
    private int size = DEFAULT_SIZE;
    private String sortBy = "id";
    private String sortDirection = "ASC";

    /**
     * Get pageable object with validation
     */
    public Pageable getPageable() {
        // Validate and clamp page number
        int pageNumber = Math.max(0, page);

        // Validate and clamp size
        int pageSize = size;
        if (pageSize <= 0 || pageSize > MAX_SIZE) {
            pageSize = DEFAULT_SIZE;
        }

        // Parse sort direction
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("DESC")) {
            direction = Sort.Direction.DESC;
        }

        // Validate sortBy field (default to "id" if empty or null)
        String sortField = sortBy;
        if (sortField == null || sortField.trim().isEmpty()) {
            sortField = "id";
        }

        return PageRequest.of(pageNumber, pageSize, direction, sortField);
    }

    /**
     * Get pageable object without sorting
     */
    public Pageable getPageableWithoutSort() {
        int pageNumber = Math.max(0, page);
        int pageSize = size <= 0 || size > MAX_SIZE ? DEFAULT_SIZE : size;
        return PageRequest.of(pageNumber, pageSize);
    }

    /**
     * Get pageable object with custom sort fields
     */
    public Pageable getPageableWithSort(Sort sort) {
        int pageNumber = Math.max(0, page);
        int pageSize = size <= 0 || size > MAX_SIZE ? DEFAULT_SIZE : size;
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    /**
     * Get the offset for manual pagination queries
     */
    public long getOffset() {
        int pageSize = size <= 0 || size > MAX_SIZE ? DEFAULT_SIZE : size;
        return (long) Math.max(0, page) * pageSize;
    }

    /**
     * Get the limit for manual pagination queries
     */
    public int getLimit() {
        return size <= 0 || size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    /**
     * Check if this is the first page
     */
    public boolean isFirstPage() {
        return page <= 0;
    }

    /**
     * Validate pagination parameters
     */
    public void validate() {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("Page size cannot exceed " + MAX_SIZE);
        }
    }
}

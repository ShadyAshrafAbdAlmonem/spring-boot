package com.inventory.inventory_management_system.common.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic pagination response wrapper for paginated API responses.
 *
 * @param <T> the type of content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean firstPage;
    private boolean lastPage;
    private boolean empty;

    /**
     * Create a PaginationResponse from a Spring Page object
     */
    public static <T> PaginationResponse<T> from(Page<T> page) {
        return new PaginationResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }

    /**
     * Create an empty PaginationResponse
     */
    public static <T> PaginationResponse<T> empty() {
        return new PaginationResponse<>(
                List.of(),
                0,
                0,
                0,
                0,
                true,
                true,
                true
        );
    }

    /**
     * Check if there are more pages
     */
    public boolean hasNext() {
        return !lastPage;
    }

    /**
     * Check if there are previous pages
     */
    public boolean hasPrevious() {
        return !firstPage;
    }

    /**
     * Get the next page number (if available)
     */
    public Integer getNextPage() {
        return hasNext() ? pageNumber + 1 : null;
    }

    /**
     * Get the previous page number (if available)
     */
    public Integer getPreviousPage() {
        return hasPrevious() ? pageNumber - 1 : null;
    }
}

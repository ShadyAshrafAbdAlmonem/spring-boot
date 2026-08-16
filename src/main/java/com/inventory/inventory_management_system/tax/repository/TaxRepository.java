package com.inventory.inventory_management_system.tax.repository;

import com.inventory.inventory_management_system.tax.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    // Find tax by name
    Optional<Tax> findByName(String name);

    // Find all active taxes
    List<Tax> findByActiveTrue();

    // Find all inactive taxes
    List<Tax> findByActiveFalse();

    // Find taxes by name containing (case-insensitive)
    List<Tax> findByNameContainingIgnoreCase(String name);

    // Find taxes by rate range
    List<Tax> findByRateBetween(BigDecimal minRate, BigDecimal maxRate);

    // Find taxes with rate greater than or equal to a value
    List<Tax> findByRateGreaterThanEqual(BigDecimal rate);

    // Find taxes with rate less than or equal to a value
    List<Tax> findByRateLessThanEqual(BigDecimal rate);

    // Check if tax name exists
    boolean existsByName(String name);

    // Check if tax name exists (excluding a specific ID)
    boolean existsByNameAndIdNot(String name, Long id);
}

package com.inventory.inventory_management_system.currency.repository;

import com.inventory.inventory_management_system.currency.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
    boolean existsByCode(String code);
    Optional<Currency> findByIsDefaultTrue();
}

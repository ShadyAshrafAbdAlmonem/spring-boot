package com.inventory.inventory_management_system.product.repository;

import com.inventory.inventory_management_system.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsBySku(String sku);
    boolean existsByBarcode(String barcode);
    Optional<Product> findBySku(String sku);
    Optional<Product> findByBarcode(String barcode);

    /**
     * Finds products that have an expiry date set, whose expiry date has already
     * passed, and that are still marked as active.
     * Used by the ExpiredProductsJob scheduler.
     */
    @Query("SELECT p FROM Product p WHERE p.expiryDate IS NOT NULL AND p.expiryDate < CURRENT_DATE AND p.active = true")
    List<Product> findExpiredProducts();

    /**
     * Finds products whose current quantity is at or below the minimum
     * quantity threshold and that are still active.
     * Used by the LowStockJob scheduler.
     */
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.minQuantity AND p.active = true")
    List<Product> findLowStockProducts();
}

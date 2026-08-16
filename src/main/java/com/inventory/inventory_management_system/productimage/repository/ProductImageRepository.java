package com.inventory.inventory_management_system.productimage.repository;

import com.inventory.inventory_management_system.productimage.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);

    @Modifying
    @Query("UPDATE ProductImage p SET p.isPrimary = false WHERE p.productId = :productId")
    void resetPrimaryImages(Long productId);
}

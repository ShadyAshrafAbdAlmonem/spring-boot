package com.inventory.inventory_management_system.product.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(unique = true, length = 100)
    private String barcode;

    @Column(length = 100)
    private String qrCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer minQuantity; // للتنبيه في حالة انخفاض المخزون

    @Column(name = "category_id")
    private String category;

    @Column(name = "brand_id")
    private String brand;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    private String imageUrl;

    /**
     * The date after which the product is considered expired and should
     * no longer be sold. Used by the ExpiredProductsJob scheduler.
     */
    private LocalDate expiryDate;

    @Builder.Default
    private Boolean active = true;
}

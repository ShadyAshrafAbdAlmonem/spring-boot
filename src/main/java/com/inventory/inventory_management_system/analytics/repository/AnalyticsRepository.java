package com.inventory.inventory_management_system.analytics.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AnalyticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public BigDecimal fetchTotalSales() {
        String jpql = "SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = 'COMPLETED'";
        return entityManager.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public long fetchLowStockCount() {
        String jpql = "SELECT COUNT(p) FROM Product p WHERE p.quantity <= p.minStockThreshold";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }
}
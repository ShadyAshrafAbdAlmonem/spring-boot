package com.inventory.inventory_management_system.report.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // مثال لـ Query المعقدة التي تجمع المبيعات خلال الفترة المقترحة
    public Double calculateTotalRevenue(LocalDateTime start, LocalDateTime end) {
        String query = "SELECT COALESCE(SUM(o.totalAmount), 0.0) FROM Order o WHERE o.createdAt BETWEEN :start AND :end";
        return entityManager.createQuery(query, Double.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();
    }
}
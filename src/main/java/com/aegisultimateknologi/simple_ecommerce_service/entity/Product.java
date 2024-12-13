package com.aegisultimateknologi.simple_ecommerce_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @Column(length = 36, unique = true)
    private String productId = UUID.randomUUID().toString();

    @Column(length = 50)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer stok;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 36, nullable = false)
    private String createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(length = 36)
    private String updatedBy;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

}

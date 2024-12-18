package com.aegisultimateknologi.simple_ecommerce_service.repository;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}

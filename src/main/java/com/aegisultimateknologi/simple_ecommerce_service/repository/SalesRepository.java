package com.aegisultimateknologi.simple_ecommerce_service.repository;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Sales;
import com.aegisultimateknologi.simple_ecommerce_service.model.projection.SalesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, String> {
    List<Sales> findByProduct(Product existingProduct);

    @Query(value = """
            SELECT
                p.created_at AS createdAt,
                s.sold AS sold,
                p.updated_at AS updatedAt,
                s.amount_product_buy AS amountProductBuy,
                s.total_price AS totalPrice,
                p.name AS nameProduct,
                p.price AS priceProduct
            FROM sales s
            JOIN product p ON s.product_id = p.product_id
            WHERE DATE(s.created_at) BETWEEN :dateStart AND :dateEnd
            """, nativeQuery = true)
    Page<SalesProjection> findByCreatedAtBetween(Date dateStart, Date dateEnd, Pageable pageable);
}

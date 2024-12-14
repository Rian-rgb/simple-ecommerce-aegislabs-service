package com.aegisultimateknologi.simple_ecommerce_service.response.sales;

import com.aegisultimateknologi.simple_ecommerce_service.response.product.GetProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSalesResponse {

    private GetProductResponse product;

    private boolean sold;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}

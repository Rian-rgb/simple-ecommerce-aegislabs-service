package com.aegisultimateknologi.simple_ecommerce_service.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private String productId;

    @NotBlank(message = "Nama product wajib diisi")
    @Size(min = 2, max = 50, message = "Nama product harus antara 3 hingga 50 karakter")
    private String name;

    private String description;

    @Min(value = 1, message = "Stok minimum 1")
    private Integer stok;

    @Positive(message = "Harga harus lebih besar dari 0")
    private BigDecimal price;
}

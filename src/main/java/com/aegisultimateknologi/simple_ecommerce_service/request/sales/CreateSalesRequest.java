package com.aegisultimateknologi.simple_ecommerce_service.request.sales;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalesRequest {

    @NotBlank(message = "productId wajib diisi")
    @Size(max = 36, message = "productId harus 36 karakter")
    private String productId;

    @Min(value = 1, message = "Jumlah pembelian minimal 1")
    private Integer amountProductBuy;
}

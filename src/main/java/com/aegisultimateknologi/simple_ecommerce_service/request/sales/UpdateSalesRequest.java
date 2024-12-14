package com.aegisultimateknologi.simple_ecommerce_service.request.sales;

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
public class UpdateSalesRequest {

    @NotBlank(message = "salesId wajib diisi")
    @Size(max = 36, message = "salesId harus 36 karakter")
    private String salesId;

    @NotBlank(message = "userId wajib diisi")
    @Size(max = 36, message = "userId harus 36 karakter")
    private String userId;
}

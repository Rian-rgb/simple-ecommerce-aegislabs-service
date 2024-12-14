package com.aegisultimateknologi.simple_ecommerce_service.request.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateActiveUserRequest {

    @NotBlank(message = "userId wajib diisi")
    @Size(max = 36, message = "userId harus 36 karakter")
    private String userId;

    private boolean enabled;
}

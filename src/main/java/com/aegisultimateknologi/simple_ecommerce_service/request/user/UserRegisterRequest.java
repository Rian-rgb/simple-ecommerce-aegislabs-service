package com.aegisultimateknologi.simple_ecommerce_service.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Username wajib diisi")
    @Size(min = 2, max = 50, message = "Username harus antara 3 hingga 50 karakter")
    private String username;

    @NotBlank(message = "Email wajib diisi")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    @Size(min = 8, max = 100, message = "Password harus antara 8 hingga 100 karakter")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password harus mengandung setidaknya satu digit, satu huruf kecil, satu huruf besar, " +
                    "satu karakter spesial, dan tidak boleh ada spasi")
    private String password;

    @NotBlank(message = "Password konfirmasi wajib diisi")
    private String passwordKonfirmasi;
}

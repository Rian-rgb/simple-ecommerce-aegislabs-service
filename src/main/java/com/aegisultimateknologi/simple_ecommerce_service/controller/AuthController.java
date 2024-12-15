package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.request.auth.AuthRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.AuthResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.AuthService;
import com.aegisultimateknologi.simple_ecommerce_service.service.JwtService;
import com.aegisultimateknologi.simple_ecommerce_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    @Operation(
            summary = "Login Pengguna",
            description = "API ini digunakan untuk memungkinkan pengguna melakukan login ke sistem menggunakan username dan password yang telah didaftarkan. Setelah berhasil login, pengguna akan menerima token autentikasi yang digunakan untuk mengakses API yang dilindungi."
    )
    public ResponseEntity<DataResponse> login(
            @RequestBody AuthRequest request
    ) {

        UserInfo userInfo = authService.authenticate(request);
        String token = jwtService.generateToken(userInfo);
        AuthResponse authResponse = AuthResponse.fromUserInfo(userInfo, token);
        if (!authResponse.isEnabled()) {
            throw new BadRequestException("Akun Anda belum diaktifkan. Silakan tunggu konfirmasi dari admin");
        }

        DataResponse response =
                new DataResponse(HttpStatus.CREATED.value(), "Login berhasil", null, authResponse);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    @Operation(
            summary = "Mendaftarkan Pengguna Baru",
            description = "API ini digunakan untuk mendaftarkan pengguna baru ke dalam sistem. " +
                    "Pengguna akan mengirimkan data pribadi seperti email, username, dan password. " +
                    "Setelah registrasi berhasil, akun pengguna akan terdaftar dengan status belum aktif dan " +
                    "memerlukan konfirmasi oleh admin sebelum dapat digunakan."
    )
    public ResponseEntity<DataResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}

package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.user.UpdateActiveUserRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class UserController {

    private final UserService userService;

    @PutMapping("/active")
    @Operation(
            summary = "Aktifkan/Mematikan Akun Pengguna",
            description = "API ini digunakan untuk mengaktifkan atau menonaktifkan akun pengguna. Dengan API ini, " +
                    "admin dapat mengubah status aktif atau tidak aktif dari akun pengguna sesuai kebutuhan."
    )
    public ResponseEntity<DataResponse> changeActiveUser(@RequestBody @Valid UpdateActiveUserRequest request) {
        return ResponseEntity.ok().body(userService.changeUserActive(request));
    }

    @GetMapping("/page")
    @Operation(
            summary = "Melihat Data Data Pengguna",
            description = "API ini digunakan untuk mengambil informasi detail mengenai data pengguna yang terdaftar " +
                    "dan yang belum terdaftar. Hanya admin yang memiliki hak akses untuk menggunakan API ini."
    )
    public ResponseEntity<PageDataResponse> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean enabled
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findByPage(pageable, enabled));
    }
}

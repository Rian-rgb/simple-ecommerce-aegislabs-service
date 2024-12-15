package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.sales.CreateSalesRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sales")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class SalesController {

    private final SalesService salesService;

    @PostMapping
    @Operation(
            summary = "Proses Transaksi Pengguna",
            description = "API ini digunakan untuk melakukan proses transaksi oleh pengguna. " +
                    "Dengan API ini, pengguna dapat membeli produk."
    )
    public ResponseEntity<DataResponse> create(@RequestBody @Valid CreateSalesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesService.create(request));
    }

    @PutMapping("/{salesId}")
    @Operation(
            summary = "Proses Refund Transaksi",
            description = "API ini digunakan untuk memproses refund atau pengembalian dana atas transaksi yang telah dilakukan oleh pengguna. Dengan API ini, pengguna atau admin dapat mengajukan refund untuk transaksi yang memenuhi syarat, termasuk alasan pengembalian dan status refund yang diajukan."
    )
    public ResponseEntity<DataResponse> updateRefund(@PathVariable("salesId") String id) {
        return ResponseEntity.ok(salesService.updateRefund(id));
    }
}

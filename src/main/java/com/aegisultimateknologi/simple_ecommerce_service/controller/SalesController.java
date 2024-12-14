package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.sales.CreateSalesRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.SalesService;
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
    public ResponseEntity<DataResponse> create(@RequestBody @Valid CreateSalesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesService.create(request));
    }

    @PutMapping("/{salesId}")
    public ResponseEntity<DataResponse> updateRefund(@PathVariable("salesId") String id) {
        return ResponseEntity.ok(salesService.updateRefund(id));
    }
}

package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Membuat Produk Baru (Admin Only)",
            description = "API ini digunakan oleh admin untuk menambahkan produk baru ke dalam sistem."
    )
    public ResponseEntity<DataResponse> create(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping
    @Operation(
            summary = "Memperbarui Produk (Admin Only)",
            description = "API ini digunakan oleh admin untuk memperbarui informasi produk yang sudah ada dalam sistem."
    )
    public ResponseEntity<DataResponse> update(@RequestBody @Valid UpdateProductRequest request) {
        return ResponseEntity.ok(productService.update(request));
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "Melihat Detail Produk",
            description = "API ini digunakan untuk melihat informasi detail mengenai produk tertentu."
    )
    public ResponseEntity<DataResponse> findById(@PathVariable("productId") String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("/{productId}")
    @Operation(
            summary = "Menghapus Produk Berdasarkan ID (Admin Only)",
            description = "API ini digunakan oleh admin untuk menghapus produk tertentu dari sistem " +
                    "berdasarkan productId yang diberikan."
    )
    public ResponseEntity<DataResponse> deleteById(@PathVariable("productId") String id) {
        return ResponseEntity.ok(productService.deleteById(id));
    }

    @GetMapping("/page")
    @Operation(
            summary = "Melihat Semua Produk",
            description = "API ini digunakan untuk melihat daftar semua produk yang tersedia di sistem."
    )
    public ResponseEntity<PageDataResponse> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.findByPage(pageable));
    }
}

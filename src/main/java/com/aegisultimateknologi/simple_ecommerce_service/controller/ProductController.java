package com.aegisultimateknologi.simple_ecommerce_service.controller;

import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PaginationGetProductResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<DataResponse> create(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping
    public ResponseEntity<DataResponse> update(@RequestBody @Valid UpdateProductRequest request) {
        return ResponseEntity.ok(productService.update(request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DataResponse> findById(@PathVariable("productId") String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DataResponse> deleteById(@PathVariable("productId") String id) {
        return ResponseEntity.ok(productService.deleteById(id));
    }

    @GetMapping("/page")
    public ResponseEntity<PaginationGetProductResponse> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.findByPage(pageable));
    }
}

package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.GetProductResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PaginationGetProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    DataResponse create(CreateProductRequest request);

    DataResponse update(UpdateProductRequest request);

    DataResponse findById(String id);

    DataResponse deleteById(String id);

    PaginationGetProductResponse findByPage(Pageable pageable);
}

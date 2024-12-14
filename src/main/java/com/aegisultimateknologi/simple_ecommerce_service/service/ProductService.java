package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    DataResponse create(CreateProductRequest request);

    DataResponse update(UpdateProductRequest request);

    DataResponse findById(String id);

    DataResponse deleteById(String id);

    PageDataResponse findByPage(Pageable pageable);
}

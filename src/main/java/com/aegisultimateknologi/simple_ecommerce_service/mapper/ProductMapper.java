package com.aegisultimateknologi.simple_ecommerce_service.mapper;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.product.GetProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    GetProductResponse mapToGetProductResponse(Product product);

    Product mapToProduct(CreateProductRequest request);

    Product mapToProduct(UpdateProductRequest request);

    Product mapToProduct(GetProductResponse request);
}

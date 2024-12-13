package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.entity.Sales;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.NotFoundException;
import com.aegisultimateknologi.simple_ecommerce_service.mapper.ProductMapper;
import com.aegisultimateknologi.simple_ecommerce_service.repository.ProductRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.SalesRepository;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.GetProductResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PaginationGetProductResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    @Override
    public DataResponse create(CreateProductRequest request) {

        Product product = ProductMapper.INSTANCE.mapToProduct(request);
        product.setProductId(UUID.randomUUID().toString());
        productRepository.save(product);

        return new DataResponse(HttpStatus.OK.value(), "Create data berhasil", null, null);

    }

    @Override
    public DataResponse update(UpdateProductRequest request) {

        productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + request.getProductId()));
        Product existingProduct = ProductMapper.INSTANCE.mapToProduct(request);
        productRepository.save(existingProduct);

        return new DataResponse(HttpStatus.OK.value(), "Update data berhasil", null, null);

    }

    @Override
    public DataResponse findById(String id) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + id));
        GetProductResponse response = ProductMapper.INSTANCE.mapToGetProductResponse(existingProduct);

        return new DataResponse(HttpStatus.OK.value(), "Get data berhasil", null, response);

    }

    @Override
    public DataResponse deleteById(String id) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + id));

        List<Sales> existingSales = salesRepository.findByProduct(existingProduct);
        if (!existingSales.isEmpty()) {
            throw new BadRequestException("Maaf, data produk tidak dapat dihapus karena produk tersebut sudah terjual");
        }

        productRepository.deleteById(id);

        return new DataResponse(HttpStatus.OK.value(), "Delete data berhasil", null, null);

    }

    @Override
    public PaginationGetProductResponse findByPage(Pageable pageable) {
        return convertToPage(productRepository.findAll(pageable));
    }

    public PaginationGetProductResponse convertToPage(Page<Product> request) {
        return PaginationGetProductResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .datas(request.getContent())
                .pageNo(request.getNumber())
                .pageSize(request.getSize())
                .totalElements(request.getTotalElements())
                .totalPages(request.getTotalPages())
                .last(request.isLast())
                .build();
    }
}

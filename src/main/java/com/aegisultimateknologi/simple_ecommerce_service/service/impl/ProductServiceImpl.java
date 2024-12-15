package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Sales;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.NotFoundException;
import com.aegisultimateknologi.simple_ecommerce_service.mapper.ProductMapper;
import com.aegisultimateknologi.simple_ecommerce_service.repository.ProductRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.SalesRepository;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.CreateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.product.UpdateProductRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.product.GetProductResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.ProductService;
import com.aegisultimateknologi.simple_ecommerce_service.util.ConvertToPageUtil;
import com.aegisultimateknologi.simple_ecommerce_service.util.ValidateRoleUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    @Override
    public DataResponse create(CreateProductRequest request) {
        try {

            UserInfo userInfo = ValidateRoleUtil.validateRole("Kasir");
            Product product = ProductMapper.INSTANCE.mapToProduct(request);
            product.setProductId(UUID.randomUUID().toString());
            product.setCreatedBy(userInfo.getUser().getUserId());
            productRepository.save(product);

            return new DataResponse(HttpStatus.OK.value(), "Create data berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse update(UpdateProductRequest request) {
        try {

            UserInfo userInfo = ValidateRoleUtil.validateRole("Kasir");
            productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + request.getProductId()));
            Product existingProduct = ProductMapper.INSTANCE.mapToProduct(request);
            existingProduct.setUpdatedBy(userInfo.getUser().getUserId());
            productRepository.save(existingProduct);

            return new DataResponse(HttpStatus.OK.value(), "Update data berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse findById(String id) {
        try {

            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + id));
            GetProductResponse response = ProductMapper.INSTANCE.mapToGetProductResponse(existingProduct);

            return new DataResponse(HttpStatus.OK.value(), "Get data berhasil", null, response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse deleteById(String id) {
        try {

            ValidateRoleUtil.validateRole("Kasir");
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Product tidak ditemukan untuk id " + id));

            List<Sales> existingSales = salesRepository.findByProduct(existingProduct);
            if (!existingSales.isEmpty()) {
                throw new BadRequestException("Maaf, data produk tidak dapat dihapus karena produk tersebut sudah terjual");
            }

            productRepository.deleteById(id);

            return new DataResponse(HttpStatus.OK.value(), "Delete data berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PageDataResponse findByPage(Pageable pageable) {
        try {

            Page<Product> productPage = productRepository.findAll(pageable);
            return ConvertToPageUtil.convertToPage(Collections.singletonList(productPage.getContent()),
                    productPage.getNumber(), productPage.getSize(),
                    productPage.getTotalElements(), productPage.getTotalPages(),
                    productPage.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

}

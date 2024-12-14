package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.entity.Sales;
import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.NotFoundException;
import com.aegisultimateknologi.simple_ecommerce_service.mapper.SalesMapper;
import com.aegisultimateknologi.simple_ecommerce_service.repository.ProductRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.SalesRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRepository;
import com.aegisultimateknologi.simple_ecommerce_service.request.sales.CreateSalesRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.SalesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    @SneakyThrows
    @Transactional
    @Override
    public DataResponse create(CreateSalesRequest request) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();

            User existingUser = userRepository.findById(userInfo.getUser().getUserId())
                    .orElseThrow(() -> new NotFoundException(
                            "Pengguna tidak ditemukan untuk id " + userInfo.getUser().getUserId()));

            Product existingProduct = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException(
                            "Product tidak ditemukan untuk id " + request.getProductId()));


            // Jika stok product sudah habis
            if (existingProduct.getStok() == 0) {
                throw new BadRequestException("Maaf, produk ini sedang habis stok. Silakan coba lagi nanti");
            }

            // Jika pembelian product melebihi stok product yang ada
            int stokNow = existingProduct.getStok() - request.getAmountProductBuy();
            if (stokNow < 0) {
                throw new BadRequestException(
                        "Jumlah yang Anda pilih melebihi stok yang tersedia. " +
                                "Harap pilih jumlah yang sesuai dengan stok");
            }

            BigDecimal totalPrice = existingProduct.getPrice().multiply(
                    BigDecimal.valueOf(request.getAmountProductBuy()));

            Sales sales = SalesMapper.INSTANCE.mapToSales(existingUser, existingProduct);
            sales.setSalesId(UUID.randomUUID().toString());
            sales.setSold(true);
            sales.setTotalPrice(totalPrice);
            sales.setAmountProductBuy(request.getAmountProductBuy());
            sales.setCreatedBy(userInfo.getUser().getUserId());
            salesRepository.save(sales);


            existingProduct.setStok(stokNow);
            productRepository.save(existingProduct);

            return new DataResponse(
                    HttpStatus.OK.value(), "Create data berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
    }

    @SneakyThrows
    @Transactional
    @Override
    public DataResponse updateRefund(String salesId) {
        try {

            Sales existingSales = salesRepository.findById(salesId)
                    .orElseThrow(() -> new NotFoundException(
                            "Transaksi tidak ditemukan untuk id " + salesId));

            Product existingProduct = productRepository.findById(existingSales.getProduct().getProductId())
                            .orElseThrow(() -> new NotFoundException(
                                    "Product pembelian tidak ditemukan untuk id " +
                                            existingSales.getProduct().getProductId()));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfo userInfo = (UserInfo) authentication.getPrincipal();

            existingSales.setSold(false);
            existingSales.setUpdatedBy(userInfo.getUser().getUserId());
            salesRepository.save(existingSales);

            Integer stokProductNow = existingProduct.getStok() + existingSales.getAmountProductBuy();
            existingProduct.setStok(stokProductNow);
            existingSales.setUpdatedBy(userInfo.getUser().getUserId());
            productRepository.save(existingProduct);

            return new DataResponse(
                    HttpStatus.OK.value(), "Refund transaksi berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
    }
}

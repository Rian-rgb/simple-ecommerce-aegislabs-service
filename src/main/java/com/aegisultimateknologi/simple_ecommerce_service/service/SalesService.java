package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.request.sales.CreateSalesRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;

public interface SalesService {

    DataResponse create(CreateSalesRequest request);

    DataResponse updateRefund(String salesId);
}

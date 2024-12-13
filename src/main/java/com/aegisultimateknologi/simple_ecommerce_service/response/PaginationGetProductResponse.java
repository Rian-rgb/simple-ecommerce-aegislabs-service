package com.aegisultimateknologi.simple_ecommerce_service.response;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationGetProductResponse {

    private int statusCode;
    private String message;
    private List<Product> datas;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}

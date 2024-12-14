package com.aegisultimateknologi.simple_ecommerce_service.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageDataResponse {

    private int statusCode;
    private String message;
    private List<Object> datas;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

}

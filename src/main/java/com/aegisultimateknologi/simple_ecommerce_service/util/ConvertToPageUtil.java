package com.aegisultimateknologi.simple_ecommerce_service.util;

import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ConvertToPageUtil {

    public static PageDataResponse convertToPage(List<Object> datas,
                                                 int pageNo, int pageSize,
                                                 long totalElements, int totalPages,
                                                 boolean last) {
        return PageDataResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .datas(datas)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(last)
                .build();
    }

}

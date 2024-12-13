package com.aegisultimateknologi.simple_ecommerce_service.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {

    private int statusCode ;
    private String message ;
    private String errorMessage ;
    private Object data;

}

package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}

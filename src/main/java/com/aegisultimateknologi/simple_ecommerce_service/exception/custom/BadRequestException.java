package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}

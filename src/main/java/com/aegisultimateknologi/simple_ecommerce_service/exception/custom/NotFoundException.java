package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}

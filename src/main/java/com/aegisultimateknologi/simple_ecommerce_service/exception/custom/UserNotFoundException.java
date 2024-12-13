package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}

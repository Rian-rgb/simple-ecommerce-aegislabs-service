package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}

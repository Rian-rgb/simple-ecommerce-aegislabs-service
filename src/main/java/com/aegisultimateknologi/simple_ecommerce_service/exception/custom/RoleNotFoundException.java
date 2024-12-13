package com.aegisultimateknologi.simple_ecommerce_service.exception.custom;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}

package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.request.auth.AuthRequest;

public interface AuthService {

    UserInfo authenticate(AuthRequest authRequest);
}

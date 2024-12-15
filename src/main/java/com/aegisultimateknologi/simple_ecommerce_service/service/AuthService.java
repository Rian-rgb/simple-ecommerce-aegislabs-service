package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.request.auth.AuthRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;

public interface AuthService {

    DataResponse login(AuthRequest request);

    UserInfo authenticate(AuthRequest authRequest);

}

package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;

public interface UserService {

    DataResponse register(UserRegisterRequest request);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

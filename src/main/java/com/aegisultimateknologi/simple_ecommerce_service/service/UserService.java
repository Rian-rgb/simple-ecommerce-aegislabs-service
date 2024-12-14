package com.aegisultimateknologi.simple_ecommerce_service.service;

import com.aegisultimateknologi.simple_ecommerce_service.request.user.UpdateActiveUserRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    DataResponse changeUserActive(UpdateActiveUserRequest request);

    PageDataResponse findByPage(Pageable pageable, boolean enabled);

    DataResponse register(UserRegisterRequest request);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}

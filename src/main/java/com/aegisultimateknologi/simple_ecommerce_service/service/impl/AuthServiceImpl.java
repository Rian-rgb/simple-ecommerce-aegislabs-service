package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.InvalidPasswordException;
import com.aegisultimateknologi.simple_ecommerce_service.request.auth.AuthRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.AuthResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.AuthService;
import com.aegisultimateknologi.simple_ecommerce_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public DataResponse login(AuthRequest request) {

        UserInfo userInfo = authenticate(request);
        String token = jwtService.generateToken(userInfo);
        AuthResponse authResponse = AuthResponse.fromUserInfo(userInfo, token);
        if (!authResponse.isEnabled()) {
            throw new BadRequestException("Akun Anda belum diaktifkan. Silakan tunggu konfirmasi dari admin");
        }

        return new DataResponse(HttpStatus.CREATED.value(), "Login berhasil", null, authResponse);

    }

    @Override
    public UserInfo authenticate(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            return (UserInfo) authentication.getPrincipal();
        } catch (Exception e) {
            throw new InvalidPasswordException(e.getMessage());
        }
    }


}

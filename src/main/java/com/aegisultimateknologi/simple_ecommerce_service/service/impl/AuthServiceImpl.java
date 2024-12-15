package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.InvalidPasswordException;
import com.aegisultimateknologi.simple_ecommerce_service.request.auth.AuthRequest;
import com.aegisultimateknologi.simple_ecommerce_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

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

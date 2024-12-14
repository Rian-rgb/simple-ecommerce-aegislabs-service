package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.config.JwtSecretConfig;
import com.aegisultimateknologi.simple_ecommerce_service.entity.Role;
import com.aegisultimateknologi.simple_ecommerce_service.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.service.JwtService;
import com.aegisultimateknologi.simple_ecommerce_service.util.DateUtil;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final JwtSecretConfig jwtSecretConfig;
    private final SecretKey signKey;

    @Override
    public String generateToken(UserInfo userInfo) {

        LocalDateTime expiredTime = LocalDateTime.now().plus(jwtSecretConfig.getJwtExpirationTime());
        Date expiredDate = DateUtil.convertLocalDateTimeToDate(expiredTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userInfo.getRole().getName());

        return Jwts.builder()
                .claims(claims)
                .subject(userInfo.getUsername())
                .issuedAt(new Date())
                .expiration(expiredDate)
                .signWith(signKey)
                .compact();

    }

    @Override
    public boolean validateToken(String token) {
        try {

            Jwts.parser()
                    .verifyWith(signKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}

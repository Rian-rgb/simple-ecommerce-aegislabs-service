package com.aegisultimateknologi.simple_ecommerce_service.response;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String userId;
    private String username;
    private String email;
    private boolean enabled;
    private String role;

    public static AuthResponse fromUserInfo(UserInfo userInfo, String token) {
        return AuthResponse.builder()
                .token(token)
                .userId(userInfo.getUser().getUserId())
                .username(userInfo.getUsername())
                .email(userInfo.getUser().getEmail())
                .enabled(userInfo.getUser().isEnabled())
                .role(userInfo.getRole().getName())
                .build();
    }
}

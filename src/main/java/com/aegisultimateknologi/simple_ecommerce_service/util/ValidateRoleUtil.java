package com.aegisultimateknologi.simple_ecommerce_service.util;

import com.aegisultimateknologi.simple_ecommerce_service.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ValidateRoleUtil {

    public static UserInfo validateRole(String roleUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        if (roleUser.equalsIgnoreCase(userInfo.getRole().getName())) {
            throw new ForbiddenException("Pengguna tidak diizinkan");
        }

        return userInfo;
    }
}

package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Role;
import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.entity.UserInfo;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.UserNotFoundException;
import com.aegisultimateknologi.simple_ecommerce_service.repository.RoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByKeyword(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "Pengguna tidak ditemukan dengan nama pengguna " + username));

        Role role = roleRepository.findByUserId(user.getUserId());

        return UserInfo.builder()
                .user(user)
                .role(role)
                .build();
    }
}

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByKeyword(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username " + username));

        List<Role> roles = roleRepository.findByUserId(user.getUserId());

        return UserInfo.builder()
                .user(user)
                .roles(roles)
                .build();
    }
}

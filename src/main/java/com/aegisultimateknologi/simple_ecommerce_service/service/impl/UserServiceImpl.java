package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.entity.UserRole;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.EmailAlreadyExistsException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.UsernameAlreadyExistsException;
import com.aegisultimateknologi.simple_ecommerce_service.mapper.UserMapper;
import com.aegisultimateknologi.simple_ecommerce_service.repository.RoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public DataResponse register(UserRegisterRequest request) {

        if (existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username sudah terdaftar");
        }
        if (existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email sudah terdaftar");
        }
        if (!request.getPassword().equals(request.getPasswordKonfirmasi())) {
            throw new BadRequestException("Password konfirmasi tidak cocok");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserMapper.INSTANCE.mapToUser(request);
        user.setPassword(encodedPassword);
        user.setEnabled(false);
        userRepository.save(user);

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(roleRepository.findByName("Kasir"))
                .build();
        userRoleRepository.save(userRole);

        return new DataResponse(HttpStatus.CREATED.value(), "Register berhasil", null, null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
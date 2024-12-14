package com.aegisultimateknologi.simple_ecommerce_service.service.impl;

import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.entity.UserRole;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.BadRequestException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.EmailAlreadyExistsException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.NotFoundException;
import com.aegisultimateknologi.simple_ecommerce_service.exception.custom.UsernameAlreadyExistsException;
import com.aegisultimateknologi.simple_ecommerce_service.mapper.UserMapper;
import com.aegisultimateknologi.simple_ecommerce_service.repository.RoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UpdateActiveUserRequest;
import com.aegisultimateknologi.simple_ecommerce_service.request.user.UserRegisterRequest;
import com.aegisultimateknologi.simple_ecommerce_service.response.DataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.response.PageDataResponse;
import com.aegisultimateknologi.simple_ecommerce_service.service.UserService;
import com.aegisultimateknologi.simple_ecommerce_service.util.ConvertToPageUtil;
import com.aegisultimateknologi.simple_ecommerce_service.util.ValidateRoleUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @SneakyThrows
    @Override
    public DataResponse changeUserActive(UpdateActiveUserRequest request) {
        try {

            ValidateRoleUtil.validateRole("Kasir");
            User existingUser = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("Pengguna tidak ditemukan untuk id " + request.getUserId()));

            existingUser.setEnabled(request.isEnabled());
            userRepository.save(existingUser);

            return new DataResponse(
                    HttpStatus.OK.value(), "Ubah status active user berhasil", null, null);


        } catch (Exception e) {
            throw e;
        }
    }

    @SneakyThrows
    @Override
    public PageDataResponse findByPage(Pageable pageable, boolean enabled) {
        try {

            ValidateRoleUtil.validateRole("Kasir");
            Page<User> userPage = userRepository.findByEnabled(pageable, enabled);
            return ConvertToPageUtil.convertToPage(Collections.singletonList(userPage.getContent()),
                    userPage.getNumber(), userPage.getSize(),
                    userPage.getTotalElements(), userPage.getTotalPages(),
                    userPage.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @SneakyThrows
    @Transactional
    @Override
    public DataResponse register(UserRegisterRequest request) {
        try {

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
            user.setUserId(UUID.randomUUID().toString());
            user.setPassword(encodedPassword);
            user.setEnabled(false);
            userRepository.save(user);

            UserRole userRole = UserRole.builder()
                    .userRoleId(UUID.randomUUID().toString())
                    .user(user)
                    .role(roleRepository.findByName("Kasir"))
                    .createdBy(user.getUserId())
                    .build();
            userRoleRepository.save(userRole);

            return new DataResponse(HttpStatus.CREATED.value(), "Register berhasil", null, null);

        } catch (Exception e) {
            throw e;
        }
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

package com.aegisultimateknologi.simple_ecommerce_service.initial;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserRole;
import com.aegisultimateknologi.simple_ecommerce_service.repository.RoleRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRepository;
import com.aegisultimateknologi.simple_ecommerce_service.repository.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InsertDataUserAdmin {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void insertDataRole() {

        User existingUserAdmin = userRepository.findByKeyword("admin").orElse(null);
        if (existingUserAdmin == null) {

            String encodedPassword = passwordEncoder.encode("admin");
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUsername("admin");
            user.setPassword(encodedPassword);
            user.setEmail("admin@gmail.com");
            user.setEnabled(true);
            userRepository.save(user);

            UserRole userRole = new UserRole();
            userRole.setUserRoleId(UUID.randomUUID().toString());
            userRole.setUser(user);
            userRole.setRole(roleRepository.findByName("Admin"));
            userRole.setCreatedBy(user.getUserId());
            userRoleRepository.save(userRole);

        }
    }
}

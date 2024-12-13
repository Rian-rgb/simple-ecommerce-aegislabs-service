package com.aegisultimateknologi.simple_ecommerce_service.initial;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Role;
import com.aegisultimateknologi.simple_ecommerce_service.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertDataRole {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void insertDataRole() {

        List<Role> existingRoles = roleRepository.findAll();
        if (existingRoles.isEmpty()) {

            List<Role> roles = new ArrayList<>();

            Role data1 = new Role();
            data1.setName("Admin");
            data1.setDescription("Role Admin");
            data1.setCreatedBy("System");

            Role data2 = new Role();
            data2.setName("Kasir");
            data2.setDescription("Role Kasir");
            data2.setCreatedBy("System");

            roles.add(data1);
            roles.add(data2);

            roleRepository.saveAll(roles);

        }
    }
}

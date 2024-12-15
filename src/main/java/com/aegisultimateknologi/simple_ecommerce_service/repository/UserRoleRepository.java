package com.aegisultimateknologi.simple_ecommerce_service.repository;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.User;
import com.aegisultimateknologi.simple_ecommerce_service.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    void deleteByUser(User existingUser);
}

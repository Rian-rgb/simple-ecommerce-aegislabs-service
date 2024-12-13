package com.aegisultimateknologi.simple_ecommerce_service.repository;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String kasir);

    @Query(value = """
            select s.role_id,
            s.name,
            s.description,
            s.created_at,
            s.updated_at from roles s
            join user_role ur on s.role_id = ur.role_id
            join users u on ur.user_id = ur.user_id
            where u.user_id =:userId
            """, nativeQuery = true)
    List<Role> findByUserId(String userId);
}

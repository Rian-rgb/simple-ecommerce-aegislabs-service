package com.aegisultimateknologi.simple_ecommerce_service.repository;

import com.aegisultimateknologi.simple_ecommerce_service.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query(value = """
            select s.role_id,
            s.name,
            s.description,
            s.created_at,
            s.created_by,
            s.updated_at,
            s.updated_by from roles s
            join user_role ur on s.role_id = ur.role_id
            join users u on ur.user_id = u.user_id
            where u.user_id =:userId
            """, nativeQuery = true)
    Role findByUserId(String userId);
}

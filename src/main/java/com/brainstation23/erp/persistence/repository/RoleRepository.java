package com.brainstation23.erp.persistence.repository;

import com.brainstation23.erp.persistence.entity.security.ERole;
import com.brainstation23.erp.persistence.entity.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}

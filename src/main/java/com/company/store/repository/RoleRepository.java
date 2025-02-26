package com.company.store.repository;

import com.company.store.model.Role;
import com.company.store.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleType type);
}


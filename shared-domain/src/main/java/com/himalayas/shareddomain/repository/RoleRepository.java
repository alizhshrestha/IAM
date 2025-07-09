package com.himalayas.shareddomain.repository;

import com.himalayas.shareddomain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(String name);
}

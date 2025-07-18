package com.himalayas.shareddomain.repository;

import com.himalayas.shareddomain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
}

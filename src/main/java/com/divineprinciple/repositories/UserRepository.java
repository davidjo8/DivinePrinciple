package com.divineprinciple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divineprinciple.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
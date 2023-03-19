package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}

package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail( String email);
}

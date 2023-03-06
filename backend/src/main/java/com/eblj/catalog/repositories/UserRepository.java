package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

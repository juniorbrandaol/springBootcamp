package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

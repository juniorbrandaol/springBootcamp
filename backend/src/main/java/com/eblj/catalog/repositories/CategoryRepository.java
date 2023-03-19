package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{/*JpaRepository é generico e espera um tipo quel o tipo do id*/
	
}

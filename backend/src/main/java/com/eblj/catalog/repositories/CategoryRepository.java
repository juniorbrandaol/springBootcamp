package com.eblj.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{/*JpaRepository é generico e espera um tipo quel o tipo do id*/
	
	

}

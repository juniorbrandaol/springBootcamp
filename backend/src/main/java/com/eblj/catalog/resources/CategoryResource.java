package com.eblj.catalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eblj.catalog.DTO.CategoryDTO;
import com.eblj.catalog.servicies.CategoryService;

@RestController
@RequestMapping(value="/api/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;

	@GetMapping
	public  List<CategoryDTO> findAll(){
		return service.findAll();
		 
	}
	
	@GetMapping("/{id}")
	public CategoryDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	
}

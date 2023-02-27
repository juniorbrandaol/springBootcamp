package com.eblj.catalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
		public CategoryDTO save(@RequestBody CategoryDTO categoryDto) {
		return service.save(categoryDto);
	}
	
	
}

package com.eblj.catalog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public  Page<CategoryDTO> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "2") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		
		return service.findAllPaged(pageRequest);
		 
	}
	
	@GetMapping("/{id}")
	public CategoryDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CategoryDTO save(@RequestBody CategoryDTO categoryDto) {
		return service.save(categoryDto);
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public CategoryDTO update( @PathVariable Long id,@RequestBody CategoryDTO catDto) {
		return service.update(id, catDto);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id) {
		 service.delete(id);
	}
	
	
}

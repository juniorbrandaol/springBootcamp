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

import com.eblj.catalog.DTO.ProductDTO;
import com.eblj.catalog.servicies.ProductService;

@RestController
@RequestMapping(value="/api/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;

	@GetMapping
	public  Page<ProductDTO> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "40") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		
		return service.findAllPaged(pageRequest);
		 
	}
	
	@GetMapping("/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductDTO save(@RequestBody ProductDTO categoryDto) {
		return service.save(categoryDto);
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ProductDTO update( @PathVariable Long id,@RequestBody ProductDTO catDto) {
		return service.update(id, catDto);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id) {
		 service.delete(id);
	}
	
	
}

package com.eblj.catalog.rest.resources;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.eblj.catalog.rest.DTO.ProductDTO;
import com.eblj.catalog.services.ProductService;

@RestController
@RequestMapping(value="/api/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public  Page<ProductDTO> findAll(
			                @RequestParam(value = "categoryId",defaultValue = "0") Long categoryId,
							@RequestParam(value = "name",defaultValue = "") String name,
							Pageable pageable
	                       )
	{
		Page<ProductDTO> list = service.findAllPaged(categoryId,name,pageable);
	     return list;
	}

	@GetMapping("/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ProductDTO save( @Valid @RequestBody ProductDTO categoryDto) {
		return service.save(categoryDto);
	}

	@PutMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ProductDTO update( @Valid @PathVariable Long id,@RequestBody ProductDTO catDto) {
		return service.update(id, catDto);
	}

	@DeleteMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id) {
		 service.delete(id);
	}


}

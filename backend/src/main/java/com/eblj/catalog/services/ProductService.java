package com.eblj.catalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.eblj.catalog.rest.DTO.ProductDTO;

public interface ProductService {
	
	public Page<ProductDTO> findAllPaged(Long categoryId,String name,Pageable pageable);
	public ProductDTO findById(Long id);
	public ProductDTO save(ProductDTO productDto);
	public ProductDTO update(Long id,ProductDTO productDto);
	public void delete(Long id);
	
	

}

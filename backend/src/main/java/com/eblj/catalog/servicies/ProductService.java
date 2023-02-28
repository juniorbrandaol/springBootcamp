package com.eblj.catalog.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.eblj.catalog.DTO.ProductDTO;

public interface ProductService {
	
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest);
	public ProductDTO findById(Long id);
	public ProductDTO save(ProductDTO productDto);
	public ProductDTO update(Long id,ProductDTO productDto);
	public void delete(Long id);
	
	

}

package com.eblj.catalog.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eblj.catalog.rest.DTO.CategoryDTO;

public interface CategoryService {
	
	public Page<CategoryDTO> findAllPaged(Pageable pageable);
	public CategoryDTO findById(Long id);
	public CategoryDTO save(CategoryDTO categoryDto);
	public CategoryDTO update(Long id,CategoryDTO categoryDto);
	public void delete(Long id);
	
	

}

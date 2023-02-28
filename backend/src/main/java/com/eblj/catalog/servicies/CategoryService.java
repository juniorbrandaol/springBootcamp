package com.eblj.catalog.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.eblj.catalog.DTO.CategoryDTO;

public interface CategoryService {
	
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest);
	public CategoryDTO findById(Long id);
	public CategoryDTO save(CategoryDTO categoryDto);
	public CategoryDTO update(Long id,CategoryDTO categoryDto);
	public void delete(Long id);
	
	

}

package com.eblj.catalog.servicies;

import java.util.List;

import com.eblj.catalog.DTO.CategoryDTO;

public interface CategoryService {
	
	public List<CategoryDTO> findAll();
	public CategoryDTO findById(Long id);

}

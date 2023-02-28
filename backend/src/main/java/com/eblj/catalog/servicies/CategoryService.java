package com.eblj.catalog.servicies;

import java.util.List;

import com.eblj.catalog.DTO.CategoryDTO;

public interface CategoryService {
	
	public List<CategoryDTO> findAll();
	public CategoryDTO findById(Long id);
	public CategoryDTO save(CategoryDTO categoryDto);
	public CategoryDTO update(Long id,CategoryDTO categoryDto);
	public void delete(Long id);

}

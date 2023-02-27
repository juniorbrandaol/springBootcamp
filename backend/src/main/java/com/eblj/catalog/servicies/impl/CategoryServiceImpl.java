package com.eblj.catalog.servicies.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.DTO.CategoryDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.servicies.CategoryService;
import com.eblj.catalog.servicies.exceptions.EntitiyNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Override
	@Transactional(readOnly =true)
	public List<CategoryDTO> findAll() {
		
		List<Category> list = repository.findAll();
		List<CategoryDTO> listDto = list.stream().map(objCategory -> new CategoryDTO(objCategory))
				.collect(Collectors.toList());
		return listDto;
	}
	
	@Override
	@Transactional(readOnly =true)
	public CategoryDTO findById(Long id) {
	   Optional<Category> obj = repository.findById(id);
	   Category entity = obj.orElseThrow(()-> new EntitiyNotFoundException("Categoria não encontrada"));
	   return new CategoryDTO(entity);
	}

	@Override
	@Transactional
	public CategoryDTO save(CategoryDTO categoryDto) {
		Category entity = new Category();
		entity.setName(categoryDto.getNome());
	    entity= repository.save(entity);
	    return new CategoryDTO(entity);
	}
	
	  
}

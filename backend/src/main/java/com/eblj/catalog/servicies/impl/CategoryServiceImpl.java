package com.eblj.catalog.servicies.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.DTO.CategoryDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.servicies.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Override
	@Transactional(readOnly =true)
	public List<CategoryDTO> findAll() {
		
		List<Category> list = repository.findAll();
		List<CategoryDTO> listDTO = list.stream().map( x-> new CategoryDTO(x))
				.collect(Collectors.toList());
		return listDTO;
	}

	  
}

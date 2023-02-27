package com.eblj.catalog.servicies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.entities.Category;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.servicies.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Override
	@Transactional(readOnly =true)
	public List<Category> findAll() {
		return repository.findAll();
	}

	  
}

package com.eblj.catalog.services.impl;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.rest.DTO.CategoryDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.services.CategoryService;
import com.eblj.catalog.services.exceptions.DataBaseException;
import com.eblj.catalog.services.exceptions.ResourceNotFoundException;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		return list.map(objCategory -> new CategoryDTO(objCategory));
		
	}

	@Override
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
		return new CategoryDTO(entity);
	}

	@Override
	@Transactional
	public CategoryDTO save(CategoryDTO categoryDto) {
		Category entity = new Category();
		entity.setName(categoryDto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Override
	@Transactional
	public CategoryDTO update(Long id,CategoryDTO categoryDto) {
		try {
			Category entety = repository.getReferenceById(id);
			entety.setName(categoryDto.getName());
			entety = repository.save(entety);
			return new CategoryDTO(entety);
		}
		catch (EntityNotFoundException e) {
		   throw new ResourceNotFoundException("Id not found "+ id);
		}
	}

	@Override
	public void delete(Long id) {	
		try {
			  repository.deleteById(id);
			}
		catch (EmptyResultDataAccessException e) {
			   throw new ResourceNotFoundException("Categoria não encontrada "+ id);
		}
		catch (DataIntegrityViolationException e) {
			 throw new  DataBaseException("Integrity violation");
		}
	}
	
	

}

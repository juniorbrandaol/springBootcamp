package com.eblj.catalog.servicies.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.DTO.CategoryDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.servicies.CategoryService;
import com.eblj.catalog.servicies.exceptions.DataBaseException;
import com.eblj.catalog.servicies.exceptions.ResourceNotFoundException;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> list = repository.findAll();
		List<CategoryDTO> listDto = list.stream().map(objCategory -> new CategoryDTO(objCategory))
				.collect(Collectors.toList());
		return listDto;
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

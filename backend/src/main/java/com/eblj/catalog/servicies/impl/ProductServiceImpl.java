package com.eblj.catalog.servicies.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.eblj.catalog.rest.DTO.ProductDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.entities.Product;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.repositories.ProductRepository;
import com.eblj.catalog.servicies.ProductService;
import com.eblj.catalog.servicies.exceptions.DataBaseException;
import com.eblj.catalog.servicies.exceptions.ResourceNotFoundException;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged( Long categoryId,String name,Pageable pageable) {
		List<Category> categories = (categoryId==0) ? null : Arrays.asList(categoryRepository.getReferenceById(categoryId));
		//JPQL
		Page<Product> list = repository.findProducts(categories,name,pageable);
		//SQL NATIVE
		//Page<Product> list = repository.findProductsSQL(category.getId(),pageable);
		return list.map(objProduct -> new ProductDTO(objProduct));

	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
		return new ProductDTO(entity,entity.getCategories());
	}

	@Override
	@Transactional
	public ProductDTO save(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto,entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Override
	@Transactional
	public ProductDTO update(Long id,ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
		    copyDtoToEntity(dto,entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
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
			   throw new ResourceNotFoundException("Produto não encontrado "+ id);
		}
		catch (DataIntegrityViolationException e) {
			 throw new  DataBaseException("Integrity violation");
		}
	}
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		
	    entity.setName(dto.getName());
	    entity.setDescription(dto.getDescription());
	    entity.setDate(dto.getDate());
	    entity.setImgUrl(dto.getImgUrl());
	    entity.setPrice(dto.getPrice());
	    
	    entity.getCategories().clear();// apenas para limpar as categorias que possam vir na entity
	    for(CategoryDTO catDto: dto.getCategories()) {
	    	Category category = categoryRepository.getReferenceById(catDto.getId());
	    	entity.getCategories().add(category);
	    }
	    
	}

}

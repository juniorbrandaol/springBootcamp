package com.eblj.catalog.servicies.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.DTO.ProductDTO;
import com.eblj.catalog.entities.Product;
import com.eblj.catalog.repositories.ProductRepository;
import com.eblj.catalog.servicies.ProductService;
import com.eblj.catalog.servicies.exceptions.DataBaseException;
import com.eblj.catalog.servicies.exceptions.ResourceNotFoundException;


@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Override
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
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
	public ProductDTO save(ProductDTO categoryDto) {
		Product entity = new Product();
	//	entity.setName(categoryDto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	@Override
	@Transactional
	public ProductDTO update(Long id,ProductDTO categoryDto) {
		try {
			Product entety = repository.getReferenceById(id);
			//entety.setName(categoryDto.getName());
			entety = repository.save(entety);
			return new ProductDTO(entety);
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
	
	

}

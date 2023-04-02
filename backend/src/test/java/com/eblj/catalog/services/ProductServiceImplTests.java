package com.eblj.catalog.services;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eblj.catalog.rest.DTO.ProductDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.entities.Product;
import com.eblj.catalog.repositories.CategoryRepository;
import com.eblj.catalog.repositories.ProductRepository;
import com.eblj.catalog.services.exceptions.DataBaseException;
import com.eblj.catalog.services.exceptions.ResourceNotFoundException;
import com.eblj.catalog.services.impl.ProductServiceImpl;
import com.eblj.catalog.tests.Factory;

@ExtendWith(SpringExtension.class)// teste de unidade
public class ProductServiceImplTests {
	
	@InjectMocks
	private ProductServiceImpl service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private  ProductDTO productDTO;
	
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		productDTO = Factory.createproductDTO();
		
		         /*********** deleteById() - métodos staticos ***********/
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);	
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		       /*********** findAll() - métodos com retorno *********/
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		       /*********** save() - métodos com retorno *********/
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		      /*********** findById() - métodos com retorno *********/
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		/*********** getReferenceById() - métodos com retorno *********/
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
	}
	
	@Test
	public void findAllPageShouldReturnPage() {
		
        Pageable pageable = Pageable.ofSize(10);
		Page<ProductDTO> result = service.findAllPaged(pageable);
		Assertions.assertNotNull(result);
		Mockito.verify(repository,Mockito.times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdShouldReturProductDTOWhenIdExists() {
		
			ProductDTO result = service.findById(existingId);
		    Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldReturResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
		    service.findById(nonExistingId);
		});
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		
		 ProductDTO result =  service.update(existingId, productDTO);
		 Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldReturnThrowWResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.update(nonExistingId, productDTO);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
	
		Assertions.assertDoesNotThrow(()->{
			service.delete(existingId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowWResourceNotFoundExceptionWhenIdDoesNotExists() {
	
		Assertions.assertThrows(ResourceNotFoundException.class,()->{
			service.delete(nonExistingId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDataIntegrityViolationExceptionWhenDependetId() {
	
		Assertions.assertThrows(DataBaseException.class,()->{
			service.delete(dependentId);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(dependentId);
	}
	
	

}

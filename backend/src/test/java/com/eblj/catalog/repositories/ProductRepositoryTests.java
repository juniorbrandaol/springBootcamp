package com.eblj.catalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.eblj.catalog.entities.Product;
import com.eblj.catalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp()throws Exception {
	  existingId= 1L;
	  nonExistingId = 100L;
	  countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		
		Assertions.assertNotNull(product);
		Assertions.assertEquals(countTotalProducts+1, product.getId());
	}
	
	//findById deveria 	retornar um Optional<Product> não vazio quando o id existir

	@Test
	public void findByIdShoulReturnNonEmptyOptionalProductWhenIdExists() {
		
		Optional<Product> result = repository.findById(existingId);	
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(existingId,result.get().getId()); 
	}
	
	
	//findById deveria retornar um Optional<Product> vazio quando o id não existir
	@Test
	public void findByIdShoulReturnEmptyOptionalProductWhenIdDoesNotExists() {
			
	  Optional<Product> result= repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
	   	repository.deleteById(existingId);
	    Optional<Product> result=repository.findById(existingId);//dindById retorna um optional
	   	Assertions.assertFalse(result.isPresent());// o metodo isPresente do optional, retorna se existe ou não um objeto
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
	
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			repository.deleteById(nonExistingId);
		});
	}
	
	

}

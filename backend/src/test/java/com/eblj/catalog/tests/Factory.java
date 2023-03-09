package com.eblj.catalog.tests;

import java.time.Instant;

import com.eblj.catalog.rest.DTO.ProductDTO;
import com.eblj.catalog.entities.Category;
import com.eblj.catalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L,"Phone","Good Phone",800.0,
				"https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"
				, Instant.parse("2023-03-02T03:00:00Z")
				);
		product.getCategories().add(new Category(1L,"Eletronics"));
		return product;
	}
	
	public static ProductDTO createproductDTO() {
		Product product = createProduct();
		return new ProductDTO(product,product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L,"Eletronics");
	}

}

package com.eblj.catalog.rest.DTO;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.eblj.catalog.entities.Category;
import com.eblj.catalog.entities.Product;


public class ProductDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	
	@Size(min = 5,max = 45,message = "Nome deve conter no mínimo {min} e no máximo {max} caracteres.")
	@NotBlank(message = "Campo obrigatório.")
	private String name;
	
	@NotBlank(message = "Campo obrigatório.")
	private String description;
	
	@Positive(message = "Informe um valor válido.")
	private Double price;
	
	@NotBlank(message = "Campo obrigatório.")
	private String imgUrl;
	
	@PastOrPresent(message = "Data inválida.")
    private Instant date;
    
    private List<CategoryDTO>  categories = new ArrayList<>();
    
    public ProductDTO() {}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	
	public ProductDTO(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

}

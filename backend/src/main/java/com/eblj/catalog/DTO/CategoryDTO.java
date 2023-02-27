package com.eblj.catalog.DTO;

import java.io.Serializable;

import com.eblj.catalog.entities.Category;

public class CategoryDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	
	public CategoryDTO() {}

	public CategoryDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public CategoryDTO(Category entitie) {
		this.id = entitie.getId();
		this.nome = entitie.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
    

}

package com.eblj.catalog.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eblj.catalog.DTO.UserDTO;
import com.eblj.catalog.DTO.UserInsertDTO;
import com.eblj.catalog.servicies.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserResource {
	
	@Autowired
	private UserService service;

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public  Page<UserDTO> findAll(Pageable pageable){
		Page<UserDTO> list = service.findAllPaged(pageable);
	     return list;
	
	}
	
	@GetMapping("/{id}")
	public UserDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserDTO save(@RequestBody UserInsertDTO dto) {
		return service.save(dto);
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO update( @PathVariable Long id,@RequestBody UserDTO dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id) {
		 service.delete(id);
	}
	
	
}

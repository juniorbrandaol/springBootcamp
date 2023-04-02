package com.eblj.catalog.rest.resources;
import com.eblj.catalog.entities.User;
import com.eblj.catalog.rest.DTO.CredentialDTO;
import com.eblj.catalog.rest.DTO.TokenDTO;
import com.eblj.catalog.security.jwt.JwtService;
import com.eblj.catalog.services.exceptions.SenhaInvalidaException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import com.eblj.catalog.rest.DTO.UserDTO;
import com.eblj.catalog.rest.DTO.UserInsertDTO;
import com.eblj.catalog.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserResource  {

	@Autowired
	private UserService service;
	@Autowired
	private JwtService jwtService;

	@GetMapping()
	@ResponseStatus(value = HttpStatus.OK)
	public  Page<UserDTO> findAll(Pageable pageable){

		Page<UserDTO> list = service.findAllPaged(pageable);
		return list;

	}
	
	@GetMapping("/{id}")
	public UserDTO findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping("/save")
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserDTO save( @Validated @RequestBody UserInsertDTO dto) {
		return service.save(dto);
	}
	
	@PutMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public UserDTO update( @Valid @PathVariable Long id,@RequestBody UserInsertDTO dto) {
		return service.update(id, dto);
	}
	@DeleteMapping(value="/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable Long id) {
		 service.delete(id);
	}

	@PostMapping("/auth")
	public TokenDTO authenticate(@RequestBody CredentialDTO dto){
		try{
			User user = new User();
			user.setEmail(dto.getEmail());
			user.setPassword(dto.getPassword());

			UserDetails usuarioAutenticado = service.authenticate(user) ;
			String token =  jwtService.accessToken(user);
			return   new TokenDTO(user.getEmail(),token);
		}
		catch (UsernameNotFoundException | SenhaInvalidaException e){
			throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED ,e.getMessage());
		}
	}


}

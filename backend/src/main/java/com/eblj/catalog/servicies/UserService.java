package com.eblj.catalog.servicies;


import com.eblj.catalog.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eblj.catalog.rest.DTO.UserDTO;
import com.eblj.catalog.rest.DTO.UserInsertDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	
	public Page<UserDTO> findAllPaged(Pageable pageable);
	public UserDTO findById(Long id);
	public UserDTO save(UserInsertDTO userDto);
	public UserDTO update(Long id,UserInsertDTO dto);
	public void delete(Long id);
	//UserDetails autenticar(CredentialDTO credenciais);

	UserDetails authenticate(Users user);
}

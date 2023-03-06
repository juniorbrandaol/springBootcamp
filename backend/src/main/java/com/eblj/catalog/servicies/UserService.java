package com.eblj.catalog.servicies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eblj.catalog.DTO.UserDTO;
import com.eblj.catalog.DTO.UserInsertDTO;

public interface UserService {
	
	public Page<UserDTO> findAllPaged(Pageable pageable);
	public UserDTO findById(Long id);
	public UserDTO save(UserInsertDTO userDto);
	public UserDTO update(Long id,UserDTO dto);
	public void delete(Long id);
	
	

}

package com.eblj.catalog.services.impl;

import java.util.*;

import com.eblj.catalog.entities.User;
import com.eblj.catalog.rest.DTO.RoleDTO;
import com.eblj.catalog.rest.DTO.UserDTO;
import com.eblj.catalog.rest.DTO.UserInsertDTO;
import com.eblj.catalog.services.exceptions.SenhaInvalidaException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eblj.catalog.entities.Role;
import com.eblj.catalog.repositories.RoleRepository;
import com.eblj.catalog.repositories.UserRepository;
import com.eblj.catalog.services.UserService;
import com.eblj.catalog.services.exceptions.DataBaseException;
import com.eblj.catalog.services.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {


    @Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public UserDTO save(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		return list.map(obj -> new UserDTO(obj));
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new UserDTO(entity);
	}

	@Override
	@Transactional
	public UserDTO update(Long id, UserInsertDTO dto) {
		try {
			User entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	@Override
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Usuário não encontrado " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user =  repository.findByEmail(userEmail)
				.orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado na base de dados"));
		Set<Role> rolesUser= user.getRoles();


		int size=0;
		String[] roles = new String[rolesUser.size()];
		for (Role x : rolesUser) {
			roles[size] = x.getAuthority();
			size++;
		}
		return org.springframework.security.core.userdetails.User
		  .builder()
		  .username(user.getEmail())
		  .password(user.getPassword())
		  .roles(roles)
		  .build();
	}
	@Override
	public UserDetails authenticate(User user) {
		UserDetails userDetails= loadUserByUsername(user.getEmail());
		boolean ifPassword = encoder.matches(user.getPassword(),userDetails.getPassword());

		if(ifPassword){
			return userDetails;
		}
		throw new SenhaInvalidaException();
	}

	private void copyDtoToEntity(UserInsertDTO dto, User entity) {

		String passwordCripto = encoder.encode(dto.getPassword());

		entity.setPassword(passwordCripto);
		entity.setFirstName(dto.getFirstName());
		entity.setCpf(dto.getCpf());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());
		
		entity.getRoles().clear();// apenas para limpar as categorias que possam vir na entity
		    for(RoleDTO roleDto: dto.getRolles()) {
		    	Role roles = roleRepository.getReferenceById(roleDto.getId());
		    	entity.getRoles().add(roles);
		    }
	}


}

package com.eblj.catalog.resources.exceptions;

import java.time.Instant;


import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.eblj.catalog.servicies.exceptions.EntitiyNotFoundException;

@RestControllerAdvice //permite que essa classe intercepte exceçoes que aconteçam nos controles(resource)
public class ResourceExceptionHandeler {
	
	@ExceptionHandler(EntitiyNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(EntitiyNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Resouce not found");
		error.setPath(request.getContextPath());
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}

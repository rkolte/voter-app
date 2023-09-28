package com.voter.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundGLobalException {

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorResponse> idNotFoundException(IdNotFoundException idNotFoundException) {
		ErrorResponse errorResponse = ErrorResponse.builder().localDateTime(LocalDateTime.now())
				.message(idNotFoundException.getMessage()).build();

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorResponse> usernameAndPasswordIssues(LoginException loginException) {
		ErrorResponse errorResponse = ErrorResponse.builder().localDateTime(LocalDateTime.now())
				.message(loginException.getMessage()).build();

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.ACCEPTED);
	}

}

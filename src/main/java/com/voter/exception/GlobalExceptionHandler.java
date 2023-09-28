package com.voter.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AgeNotValid.class)
	public ResponseEntity<ErrorResponse> voterAgeException(AgeNotValid ageNotValid) {
		ErrorResponse errorResponse = ErrorResponse.builder().localDateTime(LocalDateTime.now())
				.message(ageNotValid.getMessage()).build();

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorResponse> nationalityCheck(GenericException genericException) {
		ErrorResponse errorResponse = ErrorResponse.builder().localDateTime(LocalDateTime.now())
				.message(genericException.getMessage()).build();

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}

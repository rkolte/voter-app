package com.voter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voter.VoterAppApplication;

@RestController
@RequestMapping("/voter")
public class ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@GetMapping("/getUname")
	public ResponseEntity<String> getUsername() {
		logger.info("fetching logging user details");
		String n = SecurityContextHolder.getContext().getAuthentication().getName();

		return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getName(), HttpStatus.OK);
	}

}

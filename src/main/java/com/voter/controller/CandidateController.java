package com.voter.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voter.VoterAppApplication;
import com.voter.entity.Candidate;
import com.voter.service.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

	@Autowired
	CandidateService candidateService;

	@PostMapping
	public ResponseEntity<List<Candidate>> addCandidate(@RequestBody Candidate candidate) {
		candidateService.addCandidate(candidate);
		List<Candidate> candidates = candidateService.getAllCandidate();
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Candidate> getCandidate(@PathVariable("id") int id) {
		logger.debug("fetching data for candidate id :" + id);
		Candidate can = candidateService.getCandidate(id);
		return new ResponseEntity<Candidate>(can, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Candidate>> getAllCandidates() {

		List<Candidate> candidates = candidateService.getAllCandidate();
		return new ResponseEntity<List<Candidate>>(candidates, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<List<Candidate>> updateCandidate(@RequestBody Candidate candidate) {
		candidateService.updateCandidate(candidate);
		return getAllCandidates();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCandidate(@PathVariable("id") int id) {
		candidateService.deleteCandidate(id);
		return new ResponseEntity<>("Candidate has been successfully delete", HttpStatus.OK);
	}
}

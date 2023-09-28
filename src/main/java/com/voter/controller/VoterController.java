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

import com.voter.entity.Voter;
import com.voter.exception.CustomHttpStatus;
import com.voter.service.VoterService;

@RestController
@RequestMapping("/voter")
public class VoterController {

	private static final Logger logger = LoggerFactory.getLogger(VoterController.class);

	@Autowired
	VoterService voterService;

	@PostMapping
	public ResponseEntity<Voter> addVoter(@RequestBody Voter voter) {
		logger.debug("adding voter....");
		Voter v = voterService.addVoter(voter);
		return new ResponseEntity<Voter>(v, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Voter> getVoter(@PathVariable("id") int id) {
		logger.debug("fetching voter for id: " + id);
		Voter voter = voterService.getVoterById(id);
		return new ResponseEntity<Voter>(voter, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Voter>> getAllVoter() {
		logger.debug("fetching all voter...");
		List<Voter> voter = voterService.getAllVoter();
		return new ResponseEntity<List<Voter>>(voter, HttpStatus.OK);
	}

	@PostMapping("/vote/{vid}/candidate/{cid}")
	public ResponseEntity<String> getVoter(@PathVariable("vid") int vid, @PathVariable("cid") int cid) {
		logger.debug("voting for candidate id:" + cid);
		voterService.addVoteforCandidate(vid, cid);
		return new ResponseEntity<>("Thanks for the Voting", HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Voter> updateVoter(@RequestBody Voter voter) {
		logger.debug("updatting voter id " + voter.getId());
		Voter v = voterService.updateVoter(voter);
		return new ResponseEntity<Voter>(v, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVoter(@PathVariable("id") int id) {
		logger.debug("deleting voter " + id);
		voterService.deleteVoter(id);
		return new ResponseEntity<String>("Voter has been delete successfully", HttpStatus.valueOf(612));
	}

}
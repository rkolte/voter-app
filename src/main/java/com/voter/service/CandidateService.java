package com.voter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.voter.entity.Candidate;

@Service
public interface CandidateService {

	public Candidate addCandidate(Candidate candidate);
	public Candidate getCandidate(int id);
	public List<Candidate> getAllCandidate();
	public void deleteCandidate(int id);
	public Candidate updateCandidate(Candidate candidate);
}

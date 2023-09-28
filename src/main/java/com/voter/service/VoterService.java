package com.voter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.voter.entity.Voter;

@Service
public interface VoterService {

	public Voter addVoter(Voter v);

	public List<Voter> getAllVoter();

	public Voter getVoterById(int id);

	public Voter updateVoter(Voter v);
	
	public Optional<Voter> findByUsername(String uname);

	public Optional<Voter> findByAdhar(String adhar);

	public void addVoteforCandidate(int vid, int cid);

	public void deleteVoter(int id);
}

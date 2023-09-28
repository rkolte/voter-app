package com.voter.serviceImp;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voter.entity.Candidate;
import com.voter.entity.User;
import com.voter.entity.Voter;
import com.voter.exception.AgeNotValid;
import com.voter.exception.GenericException;
import com.voter.exception.IdNotFoundException;
import com.voter.repository.UserRepository;
import com.voter.repository.VoterRepository;
import com.voter.service.CandidateService;
import com.voter.service.VoterService;
import com.voter.utils.VoterUtils;

@Service
public class VoterServiceImpl implements VoterService {

	private static final Logger logger = LoggerFactory.getLogger(VoterServiceImpl.class);

	@Autowired
	VoterRepository voterRepository;

	@Autowired
	CandidateService candidateService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public Voter addVoter(Voter v) {
		VoterUtils.validateVoter(v);
		if (findByAdhar(v.getAdhar()).isPresent()) {
			throw new GenericException("Adhar no is alredy present please try to login or use other adhar no");
		}
		Optional<Voter> voterv = findByUsername(v.getUsername());
		if (voterv.isPresent()) {
			throw new GenericException("Username is alredy present , please try to login or use other.");
		}
		v.setVoteStatus(false);
		User user = new User();
		user.setPassword(passwordEncoder.encode(v.getPassword()));
		user.setUsername(v.getUsername());

		User u = userRepository.findByUsername(v.getUsername());
		if (u != null && !u.getPassword().equals(v.getPassword())) {

		}

		userRepository.save(user);
		return voterRepository.save(v);
	}

	@Transactional
	@Override
	public List<Voter> getAllVoter() {
		return voterRepository.findAll();
	}

	@Transactional
	@Override
	public Voter getVoterById(int id) {
		Optional<Voter> v = voterRepository.findById(id);
		return v.orElseThrow(() -> new IdNotFoundException("Given ID: " + id + " is not available"));
	}

	@Transactional
	@Override
	public Voter updateVoter(Voter v) {
		VoterUtils.validateVoter(v);

		Optional<Voter> voter = findByAdhar(v.getAdhar());

		if (voter.isPresent() && !voter.get().getAdhar().equals(v.getAdhar())) {
			throw new GenericException("Adhar no is alredy present , which is you are trying to update.");
		}
		Optional<Voter> voterv = findByUsername(v.getUsername());
		if (voterv.isPresent() && !voterv.get().getUsername().equalsIgnoreCase(v.getUsername())) {
			throw new GenericException("Username is alredy present , which is you are trying to update.");
		}
		v.setVoteStatus(false);

		User u = userRepository.findByUsername(v.getUsername());
		if (u != null && !u.getPassword().equals(v.getPassword())) {
			u.setPassword(v.getPassword());
			u.setUsername(v.getUsername());
		}
		logger.debug("Candidate has been added successfully");
		return voterRepository.save(v);

	}

	@Transactional
	@Override
	public Optional<Voter> findByUsername(String uname) {
		return voterRepository.findByUsername(uname);
	}

	@Transactional
	@Override
	public Optional<Voter> findByAdhar(String adhar) {
		Optional<Voter> voOptional = voterRepository.findByAdhar(adhar);
		return voOptional;
	}

	@Transactional
	@Override
	public void addVoteforCandidate(int vid, int cid) {

		String loginUser = SecurityContextHolder.getContext().getAuthentication().getName();
		if (loginUser != null) {
			Optional<Voter> loggedUser = voterRepository.findByUsername(loginUser);
			if (loggedUser.isPresent() && loggedUser.get().getId() != vid) {
				throw new GenericException("You are not allowed to vote for other voters");

			}
		}
		Candidate candidate = candidateService.getCandidate(cid);
		Voter voter = getVoterById(vid);
		int total_vote = 0;
		boolean voteStatus = true;
		if (null != candidate) {
			total_vote = candidate.getTotalVote();
		} else {

			throw new IdNotFoundException("Given candidate is not present, Please check correctly");
		}
		if (null != voter) {
			voteStatus = voter.isVoteStatus();
		} else {
			throw new IdNotFoundException("Given Voter is not present, Please check correctly");
		}
		if (!voteStatus) {
			candidate.setTotalVote(total_vote + 1);
			candidateService.updateCandidate(candidate);
			voter.setVoteStatus(true);
			voterRepository.save(voter);
		} else {
			throw new GenericException("User has already voted, Thank you...");
		}
		logger.debug("voting has been done successfully");
	}

	@Transactional
	@Override
	public void deleteVoter(int id) {

		voterRepository.deleteById(id);
		logger.debug("voter has been delete successfully");
	}

}
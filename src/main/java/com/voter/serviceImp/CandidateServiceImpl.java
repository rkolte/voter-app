package com.voter.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voter.controller.CandidateController;
import com.voter.entity.Candidate;
import com.voter.entity.Voter;
import com.voter.exception.GenericException;
import com.voter.exception.IdNotFoundException;
import com.voter.repository.CandidateRepository;
import com.voter.service.CandidateService;
import com.voter.utils.VoterUtils;

@Service
public class CandidateServiceImpl implements CandidateService {

	private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);
	
	@Autowired
	CandidateRepository candidateRepository;

	@Transactional
	@Override
	public Candidate addCandidate(Candidate candidate) {
		VoterUtils.validateCandidate(candidate);
		Candidate can = findByAdhar(candidate.getAdhar());

		if (can != null) {
			throw new GenericException("Adhar no is alredy present , which is you are trying to update.");
		}

		return candidateRepository.save(candidate);
	}

	@Transactional
	private Candidate findByAdhar(String adhar) {
		return candidateRepository.findByAdhar(adhar);
	}

	@Transactional
	@Override
	public Candidate getCandidate(int id) {

		Optional<Candidate> candidate = candidateRepository.findById(id);
		return candidate.orElseThrow(() -> new IdNotFoundException("Given ID: " + id + " is not available"));
	}

	@Transactional
	@Override
	public List<Candidate> getAllCandidate() {
		return candidateRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteCandidate(int id) {

		List<Candidate> liCandidates = getAllCandidate();
		long total_votes = liCandidates.stream().filter(s -> s != null).map(s -> s.getTotalVote())
				.collect(Collectors.summarizingInt(Integer::intValue)).getSum();
		Optional<Candidate> candidate = liCandidates.stream().filter(s -> s.getId() == id).findFirst();
		long eligible_votes = total_votes / 2;

		if (candidate.isPresent()) {
			if (candidate.get().getTotalVote() >= eligible_votes) {
				throw new GenericException("Candidate is not eligible for deletion...");
			}
		} else {
			throw new IdNotFoundException("Given candidate id is not present");
		}

		candidateRepository.deleteById(id);

	}

	@Transactional
	@Override
	public Candidate updateCandidate(Candidate candidate) {

		VoterUtils.validateCandidate(candidate);
		Candidate can = getCandidate(candidate.getId());

		if (can != null && !can.getAdhar().equals(candidate.getAdhar())) {
			throw new GenericException("Adhar no is alredy present , which is you are trying to update.");
		}
		if (VoterUtils.getAge(can.getDob()) != VoterUtils.getAge(candidate.getDob())) {
			throw new GenericException("Admin is not allowed to modifty age of candidate");
		}
		if (can.getTotalVote() != candidate.getTotalVote()) {
			throw new GenericException("Admin is not allowed to update votes of candidate");
		}
		return candidateRepository.save(candidate);
	}

}
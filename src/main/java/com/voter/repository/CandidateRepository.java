package com.voter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voter.entity.Candidate;

@Repository
public interface CandidateRepository  extends JpaRepository<Candidate, Integer>{

	Candidate findByAdhar(String adhar);

	
}

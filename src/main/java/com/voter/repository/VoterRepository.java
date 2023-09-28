package com.voter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.voter.entity.Voter;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Integer> {

	public Optional<Voter> findByUsername(String uname);
	public Optional<Voter> findByAdhar(String adhar);
}

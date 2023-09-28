package com.voter.utils;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;

import com.voter.entity.Candidate;
import com.voter.entity.Voter;
import com.voter.exception.AgeNotValid;
import com.voter.exception.GenericException;
import com.voter.exception.LoginException;
import com.voter.service.VoterService;

public class VoterUtils {

	public static int getAge(LocalDate localDate) {
		if (localDate == null) {
			throw new AgeNotValid("Dob should not be blank");
		}
		
		int age = Period.between(localDate, LocalDate.now()).getYears();
		return age;
	}

	public static void checkNationality(String nationality) {
		if (null == nationality) {
			throw new GenericException(" Nationality should be not blank.");
		} else if (!nationality.equalsIgnoreCase("Indian")) {
			throw new GenericException(" Nationality should be Indian.");
		}
	}

	public static void validateCandidate(Candidate candidate) {
		
		if (getAge(candidate.getDob()) < 35) {
			throw new AgeNotValid(
					"For voting age should be 35 or above 35 years and you age is: " + getAge(candidate.getDob()));
		}
		checkNationality(candidate.getNationality());
		checkAdharCard(candidate.getAdhar());

	}

	public static void validateVoter(Voter v) {
		if (getAge(v.getDob()) < 18) {
			throw new AgeNotValid("For voting age should be 18 or above 18 years and you age is: " + getAge(v.getDob()));
		}
		checkNationality(v.getNationality());
		checkUserNameAndPassword(v.getUsername(), v.getPassword());
		checkAdharCard(v.getAdhar());
	}

	private static void checkAdharCard(String adhar) {
		if (null == adhar) {
			throw new GenericException("Adhar no should not be blank");
		} else if (adhar.length() < 12 || adhar.length() > 12) {
			throw new GenericException("Adhar no should be 12 digit");
		}
	}

	public static void checkUserNameAndPassword(String uname, String pass) {

		if (uname == null) {
			throw new GenericException("UserName should not be blank");
		} else if (uname.length() < 8) {
			throw new GenericException("UserName should  be greater or equal to 8");
		}
		if (pass == null) {
			throw new GenericException("Password should not be blank");
		} else if (pass.length() < 8) {
			throw new GenericException("Password should  be greater or equal 8");
		}

	}
}

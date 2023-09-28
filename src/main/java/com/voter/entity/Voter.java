package com.voter.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.NonNull;

@Entity
public class Voter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String fname;
	private String lname;
	private String mname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private String mob;
	private String adhar;
	private String nationality;
	private String city;
	private boolean voteStatus;
	@Column(updatable = false)
	private String username;
	private String password;

	public boolean isVoteStatus() {
		return voteStatus;
	}

	public void setVoteStatus(boolean voteStatus) {
		this.voteStatus = voteStatus;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getAdhar() {
		return adhar;
	}

	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public Voter(int id, String fname, String lname, String mname, LocalDate dob, String mob, String adhar,
			String nationality, String city) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.mname = mname;
		this.dob = dob;
		this.mob = mob;
		this.adhar = adhar;
		this.nationality = nationality;
		this.city = city;
	
	}

	public Voter() {

	}
}

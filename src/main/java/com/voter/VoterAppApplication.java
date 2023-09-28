package com.voter;

import java.lang.ProcessHandle.Info;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.voter.entity.Role;
import com.voter.entity.User;
import com.voter.repository.UserRepository;

@SpringBootApplication
public class VoterAppApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(VoterAppApplication.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(VoterAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("admin data inserting.....");
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		roles.add(role);
		User u = new User();
		u.setPassword(passwordEncoder.encode("12345671"));
		u.setUsername("welcome1");
		u.setRoles(roles);
		User u1 = new User();
		u1.setPassword(passwordEncoder.encode("12345672"));
		u1.setUsername("welcome2");
		u1.setRoles(roles);

		userRepository.saveAll(Arrays.asList(u, u1));
		logger.info("admin data insertion completed");
	}

}

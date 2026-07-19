package com.product.project_rebuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.product.project_rebuild.model.Users;
import com.product.project_rebuild.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtSer;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
	
	public Users addUser(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public String verify(Users user) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		if (auth.isAuthenticated())
			return jwtSer.generateToken(user.getUsername());
		return "Faild";
	}
}

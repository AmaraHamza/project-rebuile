package com.product.project_rebuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.product.project_rebuild.model.MyUserDeatils;
import com.product.project_rebuild.model.Users;
import com.product.project_rebuild.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = repo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("The user "+ username +" not found in database");
		}
		return new MyUserDeatils(user);
	}
	

}

package com.product.project_rebuild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.project_rebuild.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);
	
}

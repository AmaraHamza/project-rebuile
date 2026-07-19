package com.product.project_rebuild.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.project_rebuild.dto.ApiResponse;
import com.product.project_rebuild.model.Users;
import com.product.project_rebuild.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/add_user")
	public ResponseEntity<ApiResponse<Users>> addUser(@RequestBody Users user) {
		Users userT = userService.addUser(user);
		if (userT != null) {
			return ResponseEntity.ok(new ApiResponse<>("The user : "+ userT.getUsername() +" is created successfully", userT));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("User cannot be created", user));
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		return userService.verify(user);
	}
}

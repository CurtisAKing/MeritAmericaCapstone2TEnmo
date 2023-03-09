package com.techelevator.tenmo.tenmo.controller;

import com.techelevator.tenmo.tenmo.model.User;
import com.techelevator.tenmo.tenmo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//controller to interact with data persisting in tenmo_user
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "user/")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("getusers")
	public List<User> getUsers(){
		return userService.getUsers();

		}
}

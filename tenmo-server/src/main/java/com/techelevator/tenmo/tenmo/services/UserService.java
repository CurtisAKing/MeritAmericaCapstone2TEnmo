package com.techelevator.tenmo.tenmo.services;

import com.techelevator.tenmo.tenmo.model.User;
import com.techelevator.tenmo.tenmo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//Carries the workload of the UserController
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> getUsers() {
		return userRepository.findAll();
	}
}

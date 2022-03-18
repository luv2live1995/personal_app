package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping(path="/demo")
public class UserController {
	
	// Dependency injection
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		
		User user = new User();
		
		user.setEmail(email);
		user.setUsername(name);
		userRepository.save(user);
		
		return "Saved";
	} 
	
	@GetMapping(path= {"/","/all","/users"})
	public @ResponseBody Iterable<User> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return userRepository.findAll();
	}
	
	@GetMapping(path= {"/test"})
	public @ResponseBody String test() {
	    // This returns a JSON or XML with the users
	    return "TEST";
	}
	
}

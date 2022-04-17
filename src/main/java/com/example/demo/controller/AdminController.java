package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(path="/admin")
public class AdminController {
	
	@Autowired
    private UserService userService;
	
	// Dependency injection
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/login");
        return modelAndView;
    }
	
	@PostMapping(path="/user")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		
		User user = new User();
		
		user.setEmail(email);
		user.setUsername(name);
		userRepository.save(user);
		
		return "Saved";
	} 
	
	@GetMapping(path= {"/users"})
	public @ResponseBody Iterable<User> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return userRepository.findAll();
	}
	
	@GetMapping(value="/admin/home")
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUsername(auth.getName());
		
		model.addAttribute("userName", "Welcome" + user.getUsername());
		return "admin/home";
	}
	
	@GetMapping(path= {"/test"})
	public @ResponseBody String test() {
	    // This returns a JSON or XML with the users
	    return "TEST";
	}
	
}

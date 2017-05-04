package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/users")
	public String getUsers(Model model){
		List<User> users = userService.getUsers();
		model.addAttribute("users",users);
		return "users";
	}

	@RequestMapping("/users/{userId}")
	public String getUserDetail(@PathVariable int userId, Model model){
		User user = userService.getUserById(userId);
		model.addAttribute("user",user);
		return "userdetail";
	}
	
}

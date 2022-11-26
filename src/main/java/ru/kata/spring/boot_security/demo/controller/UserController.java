package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("allUsers", users);
		return "user";
	}

	@RequestMapping(value = "/addUser")
	public String addUser(ModelMap model){
		model.addAttribute("user", new User());
		return "add-user";
	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@ModelAttribute("user") User user){
		userService.saveUser(user);
		return "redirect:/";
	}

	@RequestMapping(value = "/updateUser")
	public String updateUser(@RequestParam("userId") int userId, ModelMap model){
		model.addAttribute("user", userService.getUserById(userId));
		return "add-user";
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(@RequestParam("userId") int userId){
		userService.removeUserById(userId);
		return "redirect:/";
	}

	@RequestMapping(value = "/user")
	public String showUserInfo(ModelMap model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		model.addAttribute("user", user);
		return "user-info";
	}

}
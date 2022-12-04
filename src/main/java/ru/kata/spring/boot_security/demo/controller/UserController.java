package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/")
	public String printWelcome() {
		return "index";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/admin")
	public String showAll(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("allUsers", users);

		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ((PersonDetails) authentication.getPrincipal()).getUser();
		model.addAttribute("currentUser", user);

		model.addAttribute("newUser", new User());
		model.addAttribute("user", new User());

		return "index";
	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@Valid @ModelAttribute("newUser") User user){
		userService.saveUser(user);
		return "redirect:/admin";
	}
	@RequestMapping(value = "/updateUser")
	public String updateUser(@ModelAttribute("user") User user){
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/deleteUser")
	public String deleteUser(@RequestParam("userId") int userId){
		userService.removeUserById(userId);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/user")
	public String showUserInfo(ModelMap model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = ((PersonDetails) authentication.getPrincipal()).getUser();
		model.addAttribute("currentUser", user);
		return "index";
	}
}
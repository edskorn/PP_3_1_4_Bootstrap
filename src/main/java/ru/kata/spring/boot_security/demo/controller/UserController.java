package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	@RequestMapping(value = "/admin")
	public String showAll(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("allUsers", users);
		return "all-users";
	}

	@RequestMapping(value = "/addUser")
	public String addUser(ModelMap model){
		model.addAttribute("user", new User());

		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		return "add-user";
	}

	@RequestMapping(value = "/saveUser")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model){
		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		if (result.hasErrors()) {
			return "add-user";
		}
//		try {
			userService.saveUser(user);
//		} catch (Exception e) {
//			result.rejectValue("username", "UniqUsername",
//					"Пользователь с таким логином уже зарегистрирован в системе");
//			return "add-user";
//		}
		return "redirect:/admin";
	}

	@RequestMapping(value = "/updateUser")
	public String updateUser(@RequestParam("userId") int userId, ModelMap model){
		model.addAttribute("user", userService.getUserById(userId));

		List<Role> roles = userService.getAllRoles();
		model.addAttribute("allRoles", roles);

		return "add-user";
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
		model.addAttribute("user", user);
		return "user-info";
	}

//	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//	public ModelAndView handleException(HttpServletRequest req, SQLIntegrityConstraintViolationException e) {
//		long userId = Long.parseLong(req.getParameter("id"));
//
//		ModelAndView modelAndView = new ModelAndView("add-user");
//		List<Role> roles = userService.getAllRoles();
//		modelAndView.getModelMap().addAttribute("allRoles", roles);
//
//		User user = userService.getUserById(userId);
//		modelAndView.addObject(user);
//
//		BindingResult result = new BeanPropertyBindingResult(user, "user");
//		result.rejectValue("username", "UniqUsername",
//					"Пользователь с таким логином уже зарегистрирован в системе");
//		modelAndView.addObject(result.getAllErrors());
//		return modelAndView;
//	}

//	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//	public ResponseEntity<FieldError> handleException(SQLIntegrityConstraintViolationException e) {
//		return ResponseEntity
//				.status(HttpStatus.NOT_FOUND)
//				.body(new FieldError("user", "username",e.getMessage()));
//	}

}
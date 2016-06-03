package com.webapp.tokenbasedauthentication.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.tokenbasedauthentication.domain.User;
import com.webapp.tokenbasedauthentication.exceptions.UserAlreadyRegisteredException;
import com.webapp.tokenbasedauthentication.service.UserService;

@Controller
@RequestMapping ("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping (value="", method=RequestMethod.GET)
	public String registerController (Model model) {
		System.out.println("reached registercontroller");
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping (value="", method=RequestMethod.POST)
	public String registerUser (@ModelAttribute @Valid User user, BindingResult results) {
		if (results.hasErrors()) {
			return "register";
		}
		
		try {
			userService.registerUser(user);
		} catch (UserAlreadyRegisteredException ex) {
			return "register";
		}
		
		return "redirect:/";
	}
}

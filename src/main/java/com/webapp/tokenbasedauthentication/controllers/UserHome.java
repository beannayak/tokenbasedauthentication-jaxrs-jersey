package com.webapp.tokenbasedauthentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/user/home")
public class UserHome {
	@RequestMapping ("")
	public String userHome(){
		return "index";
	}
}

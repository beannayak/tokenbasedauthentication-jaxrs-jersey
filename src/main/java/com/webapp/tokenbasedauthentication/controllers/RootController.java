package com.webapp.tokenbasedauthentication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/")
public class RootController {
	@RequestMapping ("/")
	public String rootController () {
		return "index";
	}
}

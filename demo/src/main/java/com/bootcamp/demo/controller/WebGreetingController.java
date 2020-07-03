package com.bootcamp.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bootcamp.demo.model.Greeting;

@Controller
public class WebGreetingController {

	@RequestMapping(path = "web/greeting/details/{username}")
	public String getGreetingObject(@PathVariable String username, Model model) {
		Greeting greeting = new Greeting(100 , "Hello-2", username);	
		
		model.addAttribute("greeting",greeting);
		
		return "index";
	}
}

package com.bootcamp.demo.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.demo.model.Greeting;
import com.bootcamp.demo.repo.GreetingRepository;

@RestController
public class GreetingController {

	@Autowired
	GreetingRepository repo;
	
	@Value("${greetProp}")
	String defaultGreeting;
	
	@RequestMapping(path = "/greeting/default")
	public String getGreeting() {
		//return " Welcome to MS bootcamp";
		return defaultGreeting;
	}
	
	@RequestMapping(path = "/greeting/{username}")
	public String getGreeting(@PathVariable String username) {
		return " Welcome to MS bootcamp : " + username + " " + Instant.now().toString();
	}

	@RequestMapping(path = "/greeting/details/{username}")
	public Greeting getGreetingObject(@PathVariable String username) {
		Greeting greeting = new Greeting(100 , "Hello", username);		
		return greeting;
	}
	
	@RequestMapping(path = "/greetings")
	public List<Greeting> getAllGreetings() {
		List<Greeting> list = repo.findAll();		
		return list;
	}
}

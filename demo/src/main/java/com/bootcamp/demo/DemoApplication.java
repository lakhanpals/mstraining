package com.bootcamp.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.demo.model.Greeting;
import com.bootcamp.demo.repo.GreetingRepository;

@SpringBootApplication
@RefreshScope
public class DemoApplication {

	@Autowired
	GreetingRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner clr(ApplicationContext context)
	{
		return (s) -> {
			int count = context.getBeanDefinitionCount();
			System.out.println("Total bean count: " + count);
			
			List<String> beans = Arrays.asList(context.getBeanDefinitionNames());
			for (String bean: beans)
			{
				System.out.println("The bean is : " + bean);
			}
			
			populateDB();
		};
		
	}
	
	public void populateDB()
	{
		ArrayList<String> greetingList = new ArrayList<String>(Arrays.asList("Hello","hola","namaste","bonjour"));
	
		int i=0;
		for( String greeting : greetingList)
		{
			Greeting greet = new Greeting(++i, greeting, "IBM");
			repo.save(greet);			
		}
	}
}

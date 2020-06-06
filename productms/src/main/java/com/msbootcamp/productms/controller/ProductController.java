package com.msbootcamp.productms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msbootcamp.productms.model.Product;
import com.msbootcamp.productms.repo.ProductRepository;

@RestController
@RefreshScope
public class ProductController {

	@Value("${greeting: default greetings}")
	String hello;
	
	@Autowired
	ProductRepository repo;
	
	@RequestMapping(path = "/hello", method=RequestMethod.GET)
	public String Hello()
	{
		return hello;
	}
	
	@RequestMapping(path = "/v0/product", method=RequestMethod.GET)
	public List<Product> getProducts()
	{
		return repo.findAll();
	}
}

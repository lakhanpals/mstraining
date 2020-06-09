package com.msbootcamp.productms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msbootcamp.productms.model.Product;
import com.msbootcamp.productms.model.ProductDTO;
import com.msbootcamp.productms.repo.ProductRepository;
import com.msbootcamp.productms.service.ProductService;

@RestController
@RefreshScope
public class ProductController {

	@Value("${greeting: default greetings}")
	String hello;
	
	@Autowired
	ProductRepository repo;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(path = "/hello", method=RequestMethod.GET)
	public String Hello()
	{
		return hello;
	}
	
	@RequestMapping(path = "/product", method=RequestMethod.GET)
	public List<Product> getProducts()
	{
		return repo.findAll();
	}
	
	@RequestMapping(path = "V0/product/{id}", method=RequestMethod.GET)
	public ProductDTO getProduct(@PathVariable int id)
	{
		return  productService.getProduct(id);
		
		//return repo.findById(id);
	}
	

	@RequestMapping(path = "/v1/product/{id}", method=RequestMethod.GET)
	public ProductDTO getProductV1(@PathVariable int id)
	{
		return  productService.getProduct(id);
		
		//return repo.findById(id);
	}
}

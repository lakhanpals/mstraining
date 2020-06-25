package com.msbootcamp.productms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	// Only product data on MRP
	@RequestMapping(path = "V0/product/{id}", method=RequestMethod.GET)
	public Product getProduct(@PathVariable int id)
	{
		Product product = productService.getProduct(id);
		
		return  product;
		
		//return repo.findById(id);
	}
	

	// Calling discountms directly
	@RequestMapping(path = "/v1/product/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductV1(@PathVariable int id)
	{
		Product product =  productService.getProduct(id);
		ResponseEntity<ProductDTO> response;
		
		if (product != null) 
		{
			ProductDTO pDTO = productService.calculateDiscountV1(product);
			response = new ResponseEntity<ProductDTO>(pDTO,HttpStatus.FOUND);	
		}
		else
		{
			response = new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	// end point
	
	// Call discountms with eureka
	@RequestMapping(path = "/v2/product/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductV2(@PathVariable int id)
	{
		Product product =  productService.getProduct(id);
		ResponseEntity<ProductDTO> response;
		
		if (product != null) 
		{
			ProductDTO pDTO = productService.calculateDiscountV2(product);
			response = new ResponseEntity<ProductDTO>(pDTO,HttpStatus.FOUND);	
		}
		else
		{
			response = new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	// Call discountms with eureka and client loadbalancer
	@RequestMapping(path = "/v3/product/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductV3(@PathVariable int id)
	{
		Product product =  productService.getProduct(id);
		ResponseEntity<ProductDTO> response;
		
		if (product != null) 
		{
			ProductDTO pDTO = productService.calculateDiscountV3(product);
			response = new ResponseEntity<ProductDTO>(pDTO,HttpStatus.FOUND);	
		}
		else
		{
			response = new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	// Call discountms with feign client and client loadbalancer
	@RequestMapping(path = "/v4/product/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductDTO> getProductV4(@PathVariable int id)
	{
		Product product =  productService.getProduct(id);
		ResponseEntity<ProductDTO> response;
		
		if (product != null) 
		{
			ProductDTO pDTO = productService.calculateDiscountV4(product);
			response = new ResponseEntity<ProductDTO>(pDTO,HttpStatus.FOUND);	
		}
		else
		{
			response = new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
}

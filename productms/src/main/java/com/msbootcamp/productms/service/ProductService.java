package com.msbootcamp.productms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msbootcamp.productms.model.DiscountRequest;
import com.msbootcamp.productms.model.DiscountResponse;
import com.msbootcamp.productms.model.Product;
import com.msbootcamp.productms.model.ProductDTO;
import com.msbootcamp.productms.repo.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProductService {

	@Autowired
	ProductRepository repo;
	
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	LoadBalancerClient lbCLient;
	
	@Autowired
	DiscountFeignClient feignCLient;
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	public Product getProduct(int productId)
	{
		Optional<Product> product = repo.findById(productId);
		if( product.isPresent())
		{
			return product.get();
		}
		else 
			return null;
	}
	
	//Direct call i.e. with eureka
	public ProductDTO calculateDiscountV1(Product product)
	{			
		String url = "http://localhost:8081/caldisc" ;
		RestTemplate restTemplate = new RestTemplate();
		
		DiscountRequest discountRequest = createDiscountRequest(product);
		
		HttpEntity<DiscountRequest> discountHttpEntity = new HttpEntity<DiscountRequest>(discountRequest);
		
		HttpEntity<DiscountResponse> discountEntityResponse =  restTemplate.exchange(url, HttpMethod.POST, discountHttpEntity, DiscountResponse.class);
		DiscountResponse response = discountEntityResponse.getBody();
		return ceateProductResponseDTO(response,product);
	}
	

	//DiscoverClient
	public ProductDTO calculateDiscountV2(Product product)
	{
		List<ServiceInstance> instances = discoveryClient.getInstances("discountms");
		
		System.out.println("Instances of discountms found =" + instances.size());
		
		ServiceInstance instance = instances.get(0);
		
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/caldisc";

		RestTemplate restTemplate = new RestTemplate();
		
		DiscountRequest discountRequest = createDiscountRequest(product);
		
		HttpEntity<DiscountRequest> discountHttpEntity = new HttpEntity<DiscountRequest>(discountRequest);
		
		HttpEntity<DiscountResponse> discountEntityResponse =  restTemplate.exchange(url, HttpMethod.POST, discountHttpEntity, DiscountResponse.class);
		DiscountResponse response = discountEntityResponse.getBody();
		return ceateProductResponseDTO(response,product);
	}

	//ribbon backed Loadbalancer
	@HystrixCommand(fallbackMethod = "calculateDiscountFallback")
	public ProductDTO calculateDiscountV3(Product product)
	{
		ServiceInstance instance = lbCLient.choose("discountms");
		
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/caldisc";

		System.out.println("Ribbon client =" + url);
		
		RestTemplate restTemplate = new RestTemplate();
		
		DiscountRequest discountRequest = createDiscountRequest(product);
		
		HttpEntity<DiscountRequest> discountHttpEntity = new HttpEntity<DiscountRequest>(discountRequest);
		
		HttpEntity<DiscountResponse> discountEntityResponse =  restTemplate.exchange(url, HttpMethod.POST, discountHttpEntity, DiscountResponse.class);
		DiscountResponse response = discountEntityResponse.getBody();
		return ceateProductResponseDTO(response,product);
	}

	//Feign client with Loadbalancer
	public ProductDTO calculateDiscountV4(Product product)
	{

		System.out.println("Feign client =" + product.getName());
				
		DiscountRequest discountRequest = createDiscountRequest(product);		
		DiscountResponse response = feignCLient.calculateDiscount(discountRequest);
		return ceateProductResponseDTO(response,product);
	}

	private ProductDTO calculateDiscountFallback( Product p) {
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(0.0);
		pdto.setFixedCategoryDiscount(0.0);
		pdto.setOnSpotDiscount(0.0);
		pdto.setId(p.getProductId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());

		return pdto;
	}
	
	private DiscountRequest createDiscountRequest(Product p) {
		return new DiscountRequest(p.getCategory(), p.getMrp());
	}
	
	private ProductDTO ceateProductResponseDTO(DiscountResponse discountResponse, Product p) {
		ProductDTO pdto = new ProductDTO();
		pdto.setCategory(p.getCategory());
		pdto.setDrp(discountResponse.getDrp());
		pdto.setFixedCategoryDiscount(discountResponse.getFixedCategoryDiscount());
		pdto.setOnSpotDiscount(discountResponse.getOnSpotDiscount());
		pdto.setId(p.getProductId());
		pdto.setMrp(p.getMrp());
		pdto.setName(p.getName());
		pdto.setShortDescription(p.getShortDescription());
		pdto.setTags(p.getTags());

		return pdto;
	}
}

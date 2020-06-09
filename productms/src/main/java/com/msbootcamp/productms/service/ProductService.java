package com.msbootcamp.productms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.msbootcamp.productms.model.DiscountRequest;
import com.msbootcamp.productms.model.DiscountResponse;
import com.msbootcamp.productms.model.Product;
import com.msbootcamp.productms.model.ProductDTO;
import com.msbootcamp.productms.repo.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository repo;
	
	public ProductDTO getProduct(int productId)
	{
		Optional<Product> product = repo.findById(productId);
		
		if( product.isPresent())
		{
			Product productData = product.get();
			
			String url = "http://localhost:8081/caldisc" ;
			RestTemplate restTemplate = new RestTemplate();
			
			DiscountRequest discountRequest = createDiscountRequest(productData);
			
			HttpEntity<DiscountRequest> discountHttpEntity = new HttpEntity<DiscountRequest>(discountRequest);
			
			HttpEntity<DiscountResponse> discountEntityResponse =  restTemplate.exchange(url, HttpMethod.POST, discountHttpEntity, DiscountResponse.class);
			DiscountResponse response = discountEntityResponse.getBody();
			return ceateProductResponseDTO(response,productData);
		}
		else
		{
			return null;
		}
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

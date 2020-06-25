package com.msbootcamp.productms.service;

import org.springframework.stereotype.Component;

import com.msbootcamp.productms.model.DiscountRequest;
import com.msbootcamp.productms.model.DiscountResponse;
import com.msbootcamp.productms.model.ProductCategory;

@Component
public class DiscountFallback implements DiscountFeignClient {

	@Override
	public DiscountResponse calculateDiscount(DiscountRequest request) {
		// TODO Auto-generated method stub
		
		return new DiscountResponse(request.getCategory(), request.getMrp(), 0.0, 0.0,0.0); 
	}

}

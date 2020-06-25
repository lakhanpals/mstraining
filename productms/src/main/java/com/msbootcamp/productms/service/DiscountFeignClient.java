package com.msbootcamp.productms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.msbootcamp.productms.model.DiscountRequest;
import com.msbootcamp.productms.model.DiscountResponse;
import com.msbootcamp.productms.model.Product;
import com.msbootcamp.productms.model.ProductDTO;

@FeignClient(name= "discountms", fallback = DiscountFallback.class)
public interface DiscountFeignClient {

	@RequestMapping(path = "/caldisc", method = RequestMethod.POST)
	public DiscountResponse calculateDiscount(DiscountRequest request);
}

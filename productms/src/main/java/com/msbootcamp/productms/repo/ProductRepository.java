package com.msbootcamp.productms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.msbootcamp.productms.model.Product;

@Component
//@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}

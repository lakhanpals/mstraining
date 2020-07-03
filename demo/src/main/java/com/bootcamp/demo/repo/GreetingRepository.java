package com.bootcamp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.demo.model.Greeting;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Integer> {

}

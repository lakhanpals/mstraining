package com.bootcamp.demo.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Greeting {

	public Greeting() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue()
	Integer id;
	
	String greet;
	String greetwho;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGreet() {
		return greet;
	}
	public void setGreet(String greet) {
		this.greet = greet;
	}
	public String getGreetwho() {
		return greetwho;
	}
	public void setGreetwho(String greetwho) {
		this.greetwho = greetwho;
	}
	public Greeting(int id, String greet, String greetwho) {
		super();
		this.id = id;
		this.greet = greet;
		this.greetwho = greetwho;
	}
	
	
}

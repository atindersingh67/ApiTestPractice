package com.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import com.demo.util.DataService;

import io.restassured.RestAssured;

@TestConfiguration 
public class Config {
	
	
	@Autowired
	private DataService dataService;
	
	
	@PostConstruct
	public void setUp() {
		System.out.println("************************************************************");
		RestAssured.baseURI = dataService.getBaseUrl();
	}
	
	
	

}

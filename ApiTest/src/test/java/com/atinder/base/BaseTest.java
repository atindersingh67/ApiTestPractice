package com.atinder.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.atinder.common.DataService;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
	
	
	@Autowired
	private DataService dataService;
	
	
	@Before
	public void setUp() {
		RestAssured.baseURI = dataService.getBaseUrl();
	}
	
	@Test
	public void test() {
	}
	

}

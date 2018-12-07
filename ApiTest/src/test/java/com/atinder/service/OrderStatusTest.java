package com.atinder.service;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.atinder.base.BaseTest;
import com.atinder.common.DataService;
import com.atinder.common.RestCall;

import io.restassured.response.Response;


public class OrderStatusTest extends BaseTest {
	@Autowired
	private DataService dataService;
	
	@Autowired
	private RestCall restCall;

	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);
	
	@Test
	public void test_OngoingStatus() {
		logger.info("-------------------------- test_OngoingStatus------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat();
	}
	
	@Test
	public void test_OngoingStatus_after_ongoing() {
		logger.info("-------------------------- test_OngoingStatus_after_ongoing------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat();
	}
	
	
	@Test
	public void test_CompleteStatus() {
		logger.info("-------------------------- test_CompleteStatus------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat();
	}
	
	@Test
	public void test_CompleteStatus_after_creation() {
		logger.info("-------------------------- test_CompleteStatus_after_creation------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat();
	}
	
	
	@Test
	public void test_CompleteStatus_after_cancel() {
		logger.info("-------------------------- test_CompleteStatus_after_cancel------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat();
	}
	
	
	@Test
	public void test_CancelStatus() {
		logger.info("-------------------------- test_CancelStatus------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat();
	}
	
	@Test
	public void test_CancelStatus_after_creation() {
		logger.info("-------------------------- test_CancelStatus_after_creation------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat();
	}
	
	@Test
	public void test_CancelStatus_after_Complete() {
		logger.info("-------------------------- test_CancelStatus_after_Complete------------");
		Response response= restCall.placeOrder(dataService.getPlaceOrderJSon());
		
		int id= Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat();
	}
	


	
}

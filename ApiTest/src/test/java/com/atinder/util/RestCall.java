package com.atinder.util;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Service
public class RestCall {
	
	@Autowired
	private DataService dataService;
	
	public Response getOrder(int orderId){
		return get(dataService.getFetchOrder(), orderId);
	}
	
	public Response placeOrder(String body) {
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		return httpRequest.post(dataService.getPlaceOrder());
	}
	
	public Response takeOrder(int orderId) {
		return put(dataService.getTakeOrder(),orderId);
	}
	
	public Response completeOrder(int orderId) {
		return put(dataService.getCompleteOrder(),orderId);
	}
	
	public Response cancelOrder(int orderId) {
		return put(dataService.getCancelOrder(),orderId);
	}
	
}

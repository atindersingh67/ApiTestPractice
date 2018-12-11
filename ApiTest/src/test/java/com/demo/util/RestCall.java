package com.demo.util;

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
	
	/**
	 * Hit Get order details Api
	 * @param orderId : OrderId 
	 * @return : Order response 
	 */
	public Response getOrder(int orderId){
		return get(dataService.getFetchOrder(), orderId);
	}
	/**
	 * Hit Place order Api 
	 * @param body : Request payload
	 * @return : Response from API
	 */
	public Response placeOrder(String body) {
		RequestSpecification httpRequest = RestAssured.given();
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(body);
		return httpRequest.post(dataService.getPlaceOrder());
	}
	
	/**
	 * Hit Take order Api 
	 * @param orderId : order ID 
	 * @return : Response From API
	 */
	public Response takeOrder(int orderId) {
		return put(dataService.getTakeOrder(),orderId);
	}
	/**
	 *  Hit Complete order Api 
	 * @param orderId : order ID 
	 * @return : Response From API
	 */
	public Response completeOrder(int orderId) {
		return put(dataService.getCompleteOrder(),orderId);
	}
	/**
	 *  Hit Cancel order Api 
	 * @param orderId : order ID 
	 * @return : Response From API
	 */
	public Response cancelOrder(int orderId) {
		return put(dataService.getCancelOrder(),orderId);
	}
	
}

package com.atinder.service;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.atinder.base.BaseTest;
import com.atinder.common.DataService;
import com.atinder.common.RestCall;


public class PlaceOrderTest extends BaseTest{

	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;
	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);

	@Test
	public void test_placeOrder() {
		logger.info("-------------------------- test_placeOrder------------");
		restCall.placeOrder(dataService.getPlaceOrderJSon()).then().statusCode(HttpStatus.SC_CREATED).assertThat();
	}

	@Test
	public void test_placeOrderforFuture() {
		logger.info("-------------------------- test_placeOrderforFuture------------");
		restCall.placeOrder(dataService.getFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void test_placeOrder_Invalid() {
		logger.info("-------------------------- test_placeOrder_Invalid------------");
		restCall.placeOrder(dataService.getinvalidPlaceOrderJson()).then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void test_placeOrderforPast() {
		logger.info("-------------------------- test_placeOrderforPast------------");
		restCall.placeOrder(dataService.getPastOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void test_placeOrderforFuture_Invalid() {
		logger.info("-------------------------- test_placeOrderforFuture_Invalid------------");
		restCall.placeOrder(dataService.getInvalidFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}

}

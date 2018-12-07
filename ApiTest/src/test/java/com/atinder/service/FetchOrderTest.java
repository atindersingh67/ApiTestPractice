package com.atinder.service;

import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.atinder.base.BaseTest;
import com.atinder.common.DataService;
import com.atinder.common.RestCall;

import io.restassured.response.Response;

public class FetchOrderTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);
	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;

	@Test
	public void test_fetchOrderDetail_valid() {
		logger.info("-------------------------- test_fetchOrderDetail_valid------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());
		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.getOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("id",
				equalTo(id));
	}

	@Test
	public void test_fetchOrderDetail_invalid() {
		logger.info("-------------------------- test_fetchOrderDetail_invalid------------");
		restCall.getOrder(dataService.getInvalidOrderId()).then().statusCode(HttpStatus.SC_NOT_FOUND);
	}

}

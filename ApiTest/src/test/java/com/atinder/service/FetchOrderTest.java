package com.atinder.service;

import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.atinder.config.Config;
import com.atinder.config.Messages;
import com.atinder.util.DataService;
import com.atinder.util.RestCall;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Config.class)
public class FetchOrderTest {
	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);
	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;

	@Autowired
	private Messages messages;

	@Test
	public void test_fetchOrderDetail_valid() {
		logger.info("-------------------------- test_fetchOrderDetail_valid------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());
		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.getOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("id", equalTo(id));
	}

	@Test
	public void test_fetchOrderDetail_invalid() {
		logger.info("-------------------------- test_fetchOrderDetail_invalid------------");
		restCall.getOrder(dataService.getInvalidOrderId()).then().statusCode(HttpStatus.SC_NOT_FOUND).assertThat()
				.body("message", equalTo(messages.get("orderNotFound")));
	}

}

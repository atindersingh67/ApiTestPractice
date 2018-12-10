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
import com.atinder.util.DataService.ORDER_STATUS;
import com.atinder.util.RestCall;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Config.class)
public class OrderStatusTest {
	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;

	@Autowired
	private Messages messages;

	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);

	@Test
	public void test_OngoingStatus() {
		logger.info("-------------------------- test_OngoingStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.ONGOING.toString()));
	}

	@Test
	public void test_OngoingStatus_after_ongoing() {
		logger.info("-------------------------- test_OngoingStatus_after_ongoing------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messages.get("orderStatusIsNotAssigning")));
	}

	@Test
	public void test_CompleteStatus() {
		logger.info("-------------------------- test_CompleteStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.COMPLETED.toString()));
	}

	@Test
	public void test_CompleteStatus_after_creation() {
		logger.info("-------------------------- test_CompleteStatus_after_creation------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messages.get("orderStatusIsNotOngoing")));
	}

	@Test
	public void test_CompleteStatus_after_cancel() {
		logger.info("-------------------------- test_CompleteStatus_after_cancel------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messages.get("orderStatusIsNotOngoing")));
	}

	@Test
	public void test_CancelStatus() {
		logger.info("-------------------------- test_CancelStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.CANCELLED.toString()));
	}

	@Test
	public void test_CancelStatus_after_creation() {
		logger.info("-------------------------- test_CancelStatus_after_creation------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.CANCELLED.toString()));
	}

	@Test
	public void test_CancelStatus_after_Complete() {
		logger.info("-------------------------- test_CancelStatus_after_Complete------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messages.get("orderIsCompleted")));
	}

}

package com.demo.service;

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

import com.demo.config.Config;
import com.demo.config.MessagesReader;
import com.demo.util.DataService;
import com.demo.util.DataService.ORDER_STATUS;
import com.demo.util.RestCall;

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
	private MessagesReader messagesReader;

	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);

	/**
	 * Set Ongoing status for a fresh created order 
	 */
	@Test
	public void testOngoingStatus() {
		logger.info("-------------------------- testOngoingStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.ONGOING.toString()));
	}

	/**
	 * Set Ongoing status twice
	 */
	@Test
	public void testOngoingStatusAfterOngoing() {
		logger.info("-------------------------- testOngoingStatusAfterOngoing------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.takeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messagesReader.get("orderStatusIsNotAssigning")));
	}

	/**
	 * Set Complete status after Ongoing (Valid flow)
	 */
	@Test
	public void testCompleteStatus() {
		logger.info("-------------------------- testCompleteStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.COMPLETED.toString()));
	}

	/**
	 * Set Complete status on fresh order(invalid flow)
	 */
	@Test
	public void testCompleteStatusAfterCreation() {
		logger.info("-------------------------- testCompleteStatusAfterCreation------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messagesReader.get("orderStatusIsNotOngoing")));
	}


	/**
	 * Set Complete status on cancelled order(invalid flow)
	 */
	@Test
	public void testCompleteStatusAfterCancel() {
		logger.info("-------------------------- testCompleteStatusAfterCancel------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id);
		restCall.completeOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messagesReader.get("orderStatusIsNotOngoing")));
	}

	/**
	 * Set Cancel status after ongoing(valid flow)
	 */
	@Test
	public void test_CancelStatus() {
		logger.info("-------------------------- test_CancelStatus------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.CANCELLED.toString()));
	}
	/**
	 * Set Cancel status on fresh order (valid flow)
	 */
	@Test
	public void testCancelStatusAfterCreation() {
		logger.info("-------------------------- testCancelStatusAfterCreation------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_OK).assertThat().body("status",
				equalTo(ORDER_STATUS.CANCELLED.toString()));
	}
	/**
	 * Set Cancel status on completed order(invalid flow)
	 */
	@Test
	public void testCancelStatusAfterComplete() {
		logger.info("-------------------------- testCancelStatusAfterComplete------------");
		Response response = restCall.placeOrder(dataService.getPlaceOrderJSon());

		int id = Integer.parseInt(response.jsonPath().getString("id"));
		restCall.takeOrder(id);
		restCall.completeOrder(id);
		restCall.cancelOrder(id).then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).assertThat().body("message",
				equalTo(messagesReader.get("orderIsCompleted")));
	}

}

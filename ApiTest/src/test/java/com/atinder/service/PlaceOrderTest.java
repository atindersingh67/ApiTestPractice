package com.atinder.service;

import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
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
public class PlaceOrderTest {

	@Autowired
	private Messages messages;
	
	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;
	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);

	@Test
	public void test_placeOrder() {
		logger.info("-------------------------- test_placeOrder------------");
		restCall.placeOrder(dataService.getPlaceOrderJSon()).then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void test_placeOrderforFuture() {
		logger.info("-------------------------- test_placeOrderforFuture------------");
		restCall.placeOrder(dataService.getFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void test_placeOrder_Invalid() {
		logger.info("-------------------------- test_placeOrder_Invalid------------");
		restCall.placeOrder(dataService.getinvalidPlaceOrderJson()).then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.assertThat().body("message", equalTo(messages.get("errorInFiledsStop")));
	}

	@Test
	public void test_placeOrderforPast() {
		logger.info("-------------------------- test_placeOrderforPast------------");
		restCall.placeOrder(dataService.getPastOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST).
		assertThat().body("message", equalTo(messages.get("futureOrderWithPastDateError")));
	}

	@Test
	public void test_placeOrderforFuture_Invalid() {
		logger.info("-------------------------- test_placeOrderforFuture_Invalid------------");
		restCall.placeOrder(dataService.getInvalidFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST)
		.assertThat().body("message", equalTo(messages.get("errorInFiledsStop")));
	}

	@Test
	public void test_orderStops() {
		logger.info("-------------------------- test_orderStops------------");
		String orderString = dataService.getPlaceOrderJSon();
		JSONParser jsonParser = new JSONParser();
		JSONObject obj = null;
		JSONArray reqArr = null;
		JSONArray responseArray = null;
		Response response = restCall.placeOrder(orderString);
		response.then().statusCode(HttpStatus.SC_CREATED);
		try {
			obj = (JSONObject) jsonParser.parse(orderString);
			reqArr = (JSONArray) obj.get("stops");
			responseArray = (JSONArray) jsonParser.parse(response.jsonPath().getString("drivingDistancesInMeters"));
			Assert.assertEquals(reqArr.size() - 1, responseArray.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Error in converting Json");
		}

	}

}

package com.demo.service;

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

import com.demo.config.Config;
import com.demo.config.MessagesReader;
import com.demo.util.CommonUtil;
import com.demo.util.DataService;
import com.demo.util.RestCall;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(Config.class)
public class PlaceOrderTest {

	@Autowired
	private MessagesReader messagesReader;
	
	@Autowired
	private DataService dataService;

	@Autowired
	private RestCall restCall;
	private static final Logger logger = LoggerFactory.getLogger(FetchOrderTest.class);
	/**
	 * Place order with valid payload 
	 */
	@Test
	public void testPlaceOrder() {
		logger.info("-------------------------- testPlaceOrder------------");
		restCall.placeOrder(dataService.getPlaceOrderJSon()).then().statusCode(HttpStatus.SC_CREATED);
	}
	/**
	 * Place future order with valid payload 
	 */
	@Test
	public void testPlaceOrderForFuture() {
		logger.info("-------------------------- testPlaceOrderForFuture------------");
		restCall.placeOrder(dataService.getFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_CREATED);
	}
	/**
	 * Place order with invalid payload 
	 */
	@Test
	public void testPlaceOrderInvalid() {
		logger.info("-------------------------- testPlaceOrderInvalid------------");
		restCall.placeOrder(dataService.getinvalidPlaceOrderJson()).then().statusCode(HttpStatus.SC_BAD_REQUEST)
				.assertThat().body("message", equalTo(messagesReader.get("errorInFiledsStop")));
	}
	/**
	 * Place future order with past date
	 */
	@Test
	public void testPlaceOrderForPast() {
		logger.info("-------------------------- testPlaceOrderForPast------------");
		restCall.placeOrder(dataService.getPastOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST).
		assertThat().body("message", equalTo(messagesReader.get("futureOrderWithPastDateError")));
	}
	/**
	 * Place future order with invalid payload
	 */
	@Test
	public void testPlaceOrderForFutureInvalid() {
		logger.info("-------------------------- testPlaceOrderForFutureInvalid------------");
		restCall.placeOrder(dataService.getInvalidFutureOrderPlaceJSon()).then().statusCode(HttpStatus.SC_BAD_REQUEST)
		.assertThat().body("message", equalTo(messagesReader.get("errorInFiledsStop")));
	}
	
	/**
	 *Validate Stops 
	 */
	@Test
	public void testOrderStops() {
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
			logger.error("Error in converting Json" + e.getMessage(),e);
		}

	}
	
	/**
	 * validate cost when order
	 */
	@Test 
	public void verifyTripCostOutOf9to5(){
		logger.info("-------------------------- verifyTripCostOutOf9to5------------");
		Response res=restCall.placeOrder(dataService.orderCostNotIn9to5Json());
					res.then().statusCode(HttpStatus.SC_CREATED);
		
		JSONObject obj =CommonUtil.getJsonFromString(res.getBody().asString());
		JSONObject fare=(JSONObject) obj.get("fare");
		Assert.assertEquals(fare.get("amount"), dataService.getTotalFarenotin9to5());
		
		
	}

	@Test 
	public void verifyTripCostIn9to5(){
		logger.info("-------------------------- verifyTripCostOutOf9to5------------");
		Response res=restCall.placeOrder(dataService.orderCostIn9to5Json());
		res.then().statusCode(HttpStatus.SC_CREATED);
		
		JSONObject obj =CommonUtil.getJsonFromString(res.getBody().asString());
		JSONObject fare=(JSONObject) obj.get("fare");
		Assert.assertEquals(fare.get("amount"), dataService.getTotalFarein9To5());
	}
}

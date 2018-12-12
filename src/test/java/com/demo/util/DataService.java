package com.demo.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


@Service
public class DataService {
	private static final Logger logger = LoggerFactory.getLogger(DataService.class);
	
	@Value("${baseUrl}")
	private String baseUrl;
	
	@Value("${invalidOrderId}")
	private int invalidOrderId;

	@Value("${fetchOrder}")
	private String fetchOrder;

	@Value("${placeOrder}")
	private String placeOrder;
	
	@Value("${takeOrder}")
	private String takeOrder;
	
	@Value("${completeOrder}")
	private String completeOrder;
	
	@Value("${cancelOrder}")
	private String cancelOrder;
	
	@Value("${totalFarenotin9to5}")
	private String totalFarenotin9to5;

	
	@Value("${totalFarein9To5}")
	private String totalFarein9To5;

	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @return the invalidOrderId
	 */
	public int getInvalidOrderId() {
		return invalidOrderId;
	}

	
	public String getFetchOrder() {
		return fetchOrder;
	}
	
	public String getPlaceOrder() {
		return placeOrder;
	}
	
	/**
	 * @return the takeOrder
	 */
	public String getTakeOrder() {
		return takeOrder;
	}

	/**
	 * @return the completeOrder
	 */
	public String getCompleteOrder() {
		return completeOrder;
	}

	/**
	 * @return the cancelOrder
	 */
	public String getCancelOrder() {
		return cancelOrder;
	}
	
	
	/**
	 * @return the totalFarenotin9to5
	 */
	public String getTotalFarenotin9to5() {
		return totalFarenotin9to5;
	}

	/**
	 * @return the totalFarein9To5
	 */
	public String getTotalFarein9To5() {
		return totalFarein9To5;
	}

	/**
	 * Read valid place order payload from json file  
	 * @return : payload as json string 
	 */
	public String getPlaceOrderJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("payload/placeOrderRequest.json").getFile());
			} catch (IOException e) {
				logger.error("-------------------------- getPlaceOrderJSon------------" + e.getMessage(),e);
			}
			return null;
	}
	/**
	 * Read valid future order payload from json file  
	 * @return : payload as json string 
	 */
	public String getFutureOrderPlaceJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("payload/futureOrderRequest.json").getFile());
			} catch (IOException e) {
				logger.error("-------------------------- getFutureOrderPlaceJSon------------" + e.getMessage(),e);
			}
			return null;
		
	}
	/**
	 * Read past order payload from json file  
	 * @return : payload as json string 
	 */
	public String getPastOrderPlaceJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("payload/pastOrderRequest.json").getFile());
			} catch (IOException e) {
				logger.error("-------------------------- getPastOrderPlaceJSon------------" + e.getMessage(),e);
			}
			return null;
		
	}
	/**
	 * Read invalid  order payload from json file  
	 * @return : payload as json string 
	 */
	public String getinvalidPlaceOrderJson() {
			try {
				return CommonUtil.readJson(new ClassPathResource("payload/invalidPlaceOrderRequest.json").getFile());
			} catch (IOException e) {
				logger.error("-------------------------- getinvalidPlaceOrderJson------------" + e.getMessage(),e);
			}
			return null;
	}
	/**
	 * Read invalid future order payload from json file  
	 * @return : payload as json string 
	 */
	public String getInvalidFutureOrderPlaceJSon() {
		try {
			return CommonUtil.readJson(new ClassPathResource("payload/invalidFutureOrderRequest.json").getFile());
		} catch (IOException e) {
			logger.error("-------------------------- getinvalidPlaceOrderJson------------" + e.getMessage(),e);
		}
		return null;
	
	}
	
	
	/**
	 * Read Json For Order between 9 to 5
	 * @return : payload as json string 
	 */
	public String orderCostIn9to5Json() {
		try {
			return CommonUtil.readJson(new ClassPathResource("payload/orderCostIn9to5.json").getFile());
		} catch (IOException e) {
			logger.error("-------------------------- orderCostIn9to5------------" + e.getMessage(),e);
		}
		return null;
	
	}
	
	/**
	 * Read Json For Order Out of  9 to 5
	 * @return : payload as json string 
	 */
	public String orderCostNotIn9to5Json() {
		try {
			return CommonUtil.readJson(new ClassPathResource("payload/orderCostOutOf9to5.json").getFile());
		} catch (IOException e) {
			logger.error("-------------------------- orderCostNotIn9to5------------" + e.getMessage(),e);
		}
		return null;
	
	}
	/**************Status of Orders********************/
	public enum ORDER_STATUS {
		ONGOING, COMPLETED, CANCELLED;
	}
	
}

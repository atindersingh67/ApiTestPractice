package com.atinder.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.atinder.common.CommonUtil;

@Service
public class DataService {

	
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

	public String getPlaceOrderJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("placeOrderRequest.json").getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return baseUrl;
	}
	
	public String getFutureOrderPlaceJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("futureOrderRequest.json").getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return baseUrl;
		
	}
	
	public String getPastOrderPlaceJSon() {
			try {
				return CommonUtil.readJson(new ClassPathResource("pastOrderRequest.json").getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return baseUrl;
		
	}
	
	public String getinvalidPlaceOrderJson() {
			try {
				return CommonUtil.readJson(new ClassPathResource("invalidPlaceOrderRequest.json").getFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return baseUrl;
	}
	public String getInvalidFutureOrderPlaceJSon() {
		try {
			return CommonUtil.readJson(new ClassPathResource("invalidFutureOrderRequest.json").getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseUrl;
	
}
	
}

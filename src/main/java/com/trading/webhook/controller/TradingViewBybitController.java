package com.trading.webhook.controller;

import org.springframework.http.ResponseEntity;

import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;

/**
 * TradingView Controller - REST Services Layer - Spring Boot
 *
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public interface TradingViewBybitController {


	/**
	 * set order for demo acount- Spring Boot
	 *
	 * @param BybitRequest see {@link BybitRequest}
	 * @return ResponseEntity<BybitResponse> see {@link BybitResponse}
	 */
	public  ResponseEntity<BybitResponse> setOrderDemo(BybitRequest dto);
	
	/**
	 * set order for Real acount- Spring Boot
	 *
	 * @param BybitRequest see {@link BybitRequest}
	 * @return ResponseEntity<BybitResponse> see {@link BybitResponse}
	 */
	public  ResponseEntity<BybitResponse> setOrderReal(BybitRequest dto);
	
	
	
}

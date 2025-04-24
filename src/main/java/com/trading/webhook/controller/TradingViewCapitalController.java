package com.trading.webhook.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.trading.webhook.dto.CapitalLoginResponse;
import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.OperationsDto;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.dto.TradingViewResponse;

/**
 * TradingView Controller - REST Services Layer - Spring Boot
 *
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public interface TradingViewCapitalController {


	/**
	 * Reciver payload Method for Demo acount.- Spring Boot
	 *
	 * @param TradingViewResponse see {@link TradingViewResponse}
	 * @return ResponseEntity<TradingViewResponse> see {@link TradingViewResponse}
	 */
	public  ResponseEntity<ConfirmResponse> getPayLoadTradingViewDemo(TradingViewRequest dto);
	
	/**
	 * Reciver payload Method for Real acount- Spring Boot
	 *
	 * @param TradingViewResponse see {@link TradingViewResponse}
	 * @return ResponseEntity<TradingViewResponse> see {@link TradingViewResponse}
	 */
	public  ResponseEntity<CapitalResponse> getPayLoadTradingViewReal(TradingViewRequest dto);
	
	public ResponseEntity<CapitalLoginResponse> getTokens(TradingViewRequest dto);
	
	public ResponseEntity<List<OperationsDto>> getOperations() ;
	
	
	
}

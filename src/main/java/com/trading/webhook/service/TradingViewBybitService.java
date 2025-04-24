package com.trading.webhook.service;

import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;


/**
 * TradingView - Bybit - Service
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public interface TradingViewBybitService {

	
	/**
	 * set order for demo acount
	 *
	 * @param TradingViewRequest see {@link BybitRequest}
	 * @return BybitResponse @see {@link BybitResponse}
	 */
	public BybitResponse setOrderDemo(BybitRequest dto);
	
	/**
	 * set order for Real acount- Spring Boot
	 *
	 * @param TradingViewRequest see {@link BybitRequest}
	 * @return BybitResponse @see {@link BybitResponse}
	 */
	public BybitResponse setOrderReal(BybitRequest dto);
	
}

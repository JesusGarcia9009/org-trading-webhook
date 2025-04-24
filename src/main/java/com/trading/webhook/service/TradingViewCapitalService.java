package com.trading.webhook.service;

import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.dto.TradingViewResponse;


/**
 * Cause Service
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public interface TradingViewCapitalService {

	
	/**
	 * get trx trading view
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return TradingViewResponse @see {@link TradingViewResponse}
	 */
	public ConfirmResponse getPayLoadTradingViewDemo(TradingViewRequest dto);
	
	/**
	 * get trx trading view
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return TradingViewResponse @see {@link TradingViewResponse}
	 */
	public CapitalResponse getPayLoadTradingViewReal(TradingViewRequest dto);
	
}

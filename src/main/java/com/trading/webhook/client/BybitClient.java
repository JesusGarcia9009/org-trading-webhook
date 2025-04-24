package com.trading.webhook.client;

import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;


/**
 * BybitClient - conect to api of bybit exchange
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
public interface BybitClient {

	
	/**
	 * create trx in capital.com demo acount
	 *
	 * @param BybitRequest see {@link BybitRequest}
	 * @return BybitResponse @see {@link BybitResponse}
	 */
	public BybitResponse createTrxDemo(BybitRequest dto);
	
	/**
	 * create trx in capital.com real acount
	 *
	 * @param BybitRequest see {@link BybitRequest}
	 * @return BybitResponse @see {@link BybitResponse}
	 */
	public BybitResponse createTrxReal(BybitRequest dto);
	
}

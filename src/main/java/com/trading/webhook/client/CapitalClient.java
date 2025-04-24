package com.trading.webhook.client;

import com.trading.webhook.dto.CapitalLoginResponse;
import com.trading.webhook.dto.CapitalRequest;
import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.dto.TradingViewResponse;


/**
 * Capital Client - conect to api of capital.com
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */

public interface CapitalClient {

	
	/**
	 * create trx in capital.com demo acount
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return TradingViewResponse @see {@link TradingViewResponse}
	 */
	public CapitalResponse createTrx(TradingViewRequest dto, CapitalRequest request, Boolean isDemo);
	
	/**
	 * confirm trx in capital.com demo acount
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return ConfirmResponse @see {@link ConfirmResponse}
	 */
	public ConfirmResponse confirmTrx(TradingViewRequest dto,String dealId, Boolean isDemo);
	
	/**
	 * close operation trx in capital.com real acount
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return TradingViewResponse @see {@link TradingViewResponse}
	 */
	public CapitalResponse delete(TradingViewRequest dto,String affectedDeals, Boolean isDemo);
	
	/**
	 * get data login in capital.com real acount
	 *
	 * @param TradingViewRequest see {@link TradingViewRequest}
	 * @return TradingViewResponse @see {@link TradingViewResponse}
	 */
	public CapitalLoginResponse getLoginEncrip(TradingViewRequest dto);
	
}

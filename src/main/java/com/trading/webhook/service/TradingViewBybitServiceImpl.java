package com.trading.webhook.service;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;

import org.springframework.stereotype.Service;

import com.trading.webhook.client.BybitClient;
import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;
import com.trading.webhook.repository.OperationsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TradingView Service implement
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TradingViewBybitServiceImpl implements TradingViewBybitService {
	
	private final BybitClient bybitClient;
	private final OperationsRepository operationsRepository;

	@Override
	public BybitResponse setOrderDemo(BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		BybitResponse response = null;
		
		if(dto.getOrderFlow().equalsIgnoreCase("LONG")) {}
		if(dto.getOrderFlow().equalsIgnoreCase("CLOSE LONG")) { }
		if(dto.getOrderFlow().equalsIgnoreCase("SHORT")) { }
		if(dto.getOrderFlow().equalsIgnoreCase("CLOSE SHORT")) { }
		
		bybitClient.createTrxDemo(dto);
		
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return response;
	}

	@Override
	public BybitResponse setOrderReal(BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		BybitResponse response = null;
		
		if(dto.getOrderFlow().equalsIgnoreCase("LONG")) {}
		if(dto.getOrderFlow().equalsIgnoreCase("CLOSE LONG")) { }
		if(dto.getOrderFlow().equalsIgnoreCase("SHORT")) { }
		if(dto.getOrderFlow().equalsIgnoreCase("CLOSE SHORT")) { }
		
		bybitClient.createTrxReal(dto);
		
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return response;
	}
}

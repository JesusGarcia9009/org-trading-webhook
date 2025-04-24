package com.trading.webhook.controller;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;
import com.trading.webhook.service.TradingViewBybitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tradingview-bybit")
public class TradingViewBybitControllerImpl implements TradingViewBybitController {
	
	/**
	 * Global variables
	 */
	private final TradingViewBybitService tradingViewBybitService;
	
	@Override
	@PostMapping("/demo")
	public ResponseEntity<BybitResponse> setOrderDemo(@RequestBody BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		BybitResponse result = tradingViewBybitService.setOrderDemo(dto);
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(result);
	}

	@Override
	@PostMapping("/real")
	public ResponseEntity<BybitResponse> setOrderReal(@RequestBody BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		BybitResponse result = tradingViewBybitService.setOrderReal(dto);
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(result);
	}

	
	

	
	
	
}

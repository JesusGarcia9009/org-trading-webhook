package com.trading.webhook.controller;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;
import static com.trading.webhook.utils.ConstantUtil.LOG_START_OPERATION;

import java.util.List;

import static com.trading.webhook.utils.ConstantUtil.LOG_END_OPERATION;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.webhook.client.CapitalClient;
import com.trading.webhook.dto.CapitalLoginResponse;
import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.OperationsDto;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.entity.Operations;
import com.trading.webhook.repository.OperationsRepository;
import com.trading.webhook.service.TradingViewCapitalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tradingview-capital")
public class TradingViewCapitalControllerImpl implements TradingViewCapitalController {
	
	/**
	 * Global variables
	 */
	private final TradingViewCapitalService tradingViewService;
	private final CapitalClient capitalClient;
	private final OperationsRepository operationsRepository;
	
	@Override
	@PostMapping("/demo")
	public ResponseEntity<ConfirmResponse> getPayLoadTradingViewDemo(@RequestBody TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		log.info(String.format(LOG_START_OPERATION, dto.getOrderFlow()));
		ConfirmResponse result = tradingViewService.getPayLoadTradingViewDemo(dto);
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		log.info(String.format(LOG_END_OPERATION, dto.getOrderFlow()));
		return ResponseEntity.ok(result);
	}
	
	@Override
	@PostMapping("/real")
	public ResponseEntity<CapitalResponse> getPayLoadTradingViewReal(@RequestBody TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalResponse result = tradingViewService.getPayLoadTradingViewReal(dto);
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(result);
	}

	@Override
	@PostMapping("/get-tokens")
	public ResponseEntity<CapitalLoginResponse> getTokens(@RequestBody TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalLoginResponse result = capitalClient.getLoginEncrip(dto);
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(result);
	}
	
	@Override
	@GetMapping("/operations")
	public ResponseEntity<List<OperationsDto>> getOperations() {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		List<OperationsDto> listado = (List<OperationsDto>) operationsRepository.findAllOperations();
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return ResponseEntity.ok(listado);
	}
	
	

	
	

	
	
	
}

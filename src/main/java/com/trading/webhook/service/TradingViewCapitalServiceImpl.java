package com.trading.webhook.service;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_NOT_FOUND;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.trading.webhook.client.CapitalClient;
import com.trading.webhook.dto.CapitalRequest;
import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.entity.Operations;
import com.trading.webhook.repository.OperationsRepository;
import com.trading.webhook.utils.Utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
public class TradingViewCapitalServiceImpl implements TradingViewCapitalService {

//	private final BybitClient bybitClient;
	private final CapitalClient capitalClient;
	private final OperationsRepository operationsRepository;

	@Override
	public ConfirmResponse getPayLoadTradingViewDemo(TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalResponse response = null;
		ConfirmResponse resp = null;
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		try {

			if (dto.getOrderFlow().equalsIgnoreCase("LONG")) {
				CapitalRequest request = Utils.createCapitalRequest(dto);
				response = capitalClient.createTrx(dto, request, Boolean.TRUE);
				resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.TRUE);

				Operations model = new Operations();
				model.setDealid(resp.getDealId());
				model.setDealreference(resp.getDealReference());
				model.setAffecteddeals(Utils.getAffectedDeals(resp.getAffectedDeals()));
				model.setCloseDate(null);
				model.setDirection(request.getDirection());
				model.setEpic(request.getEpic());
				model.setGuaranteedstop(request.getGuaranteedStop());
				model.setOpenDate(new Date());
				model.setOrderSize(request.getSize());
				model.setProfitlevel( Objects.nonNull(request.getProfitDistance()) ? new BigDecimal(request.getProfitDistance()) : null);
				model.setStoplevel( Objects.nonNull(request.getStopDistance()) ? new BigDecimal(request.getStopDistance()) : null);
				model.setState("OPEN");
				model.setAccount("DEMO");

				operationsRepository.save(model);
			}

			if (dto.getOrderFlow().equalsIgnoreCase("CLOSE LONG")) {
				Operations model = operationsRepository.findByEpicAndStateAndAccountAndDirection(dto.getTicker(), "OPEN", "DEMO", "BUY");
				if (Objects.nonNull(model)) {
					response = capitalClient.delete(dto, model.getAffecteddeals(), Boolean.TRUE);
					resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.TRUE);
					model.setState("CLOSE");
					model.setCloseDate(new Date());
					operationsRepository.save(model);
				} else {
					log.info(LOG_NOT_FOUND);
				}
			}

			if (dto.getOrderFlow().equalsIgnoreCase("SHORT")) {
				CapitalRequest request = Utils.createCapitalRequest(dto);
				response = capitalClient.createTrx(dto, request, Boolean.TRUE);
				resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.TRUE);

				Operations model = new Operations();
				model.setDealid(resp.getDealId());
				model.setDealreference(resp.getDealReference());
				model.setAffecteddeals(Utils.getAffectedDeals(resp.getAffectedDeals()));
				model.setCloseDate(null);
				model.setDirection(request.getDirection());
				model.setEpic(request.getEpic());
				model.setGuaranteedstop(request.getGuaranteedStop());
				model.setOpenDate(new Date());
				model.setOrderSize(request.getSize());
				model.setProfitlevel( Objects.nonNull(request.getProfitDistance()) ? new BigDecimal(request.getProfitDistance()) : null);
				model.setStoplevel( Objects.nonNull(request.getStopDistance()) ? new BigDecimal(request.getStopDistance()) : null);
				model.setState("OPEN");
				model.setAccount("DEMO");

				operationsRepository.save(model);
			}

			if (dto.getOrderFlow().equalsIgnoreCase("CLOSE SHORT")) {
				Operations model = operationsRepository.findByEpicAndStateAndAccountAndDirection(dto.getTicker(), "OPEN", "DEMO", "SELL");
				if (Objects.nonNull(model)) {
					response = capitalClient.delete(dto, model.getAffecteddeals(), Boolean.TRUE);
					resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.TRUE);
					model.setState("CLOSE");
					model.setCloseDate(new Date());
					operationsRepository.save(model);
				} else {
					log.info(LOG_NOT_FOUND);
				}
			}
			
			Message.creator(new PhoneNumber(ACCOUNT_NUMBER_TO), new PhoneNumber(ACCOUNT_NUMBER),
					"Se ha generado satisfactoriamente una operacion de tipo " + dto.getOrderFlow()).create();

		} catch (Exception e) {
			Message.creator(new PhoneNumber(ACCOUNT_NUMBER_TO), new PhoneNumber(ACCOUNT_NUMBER),
					"Ha ocurrido un error en una operacion de tipo " + dto.getOrderFlow() + " , Por favor revise sus transacciones en Capital.com").create();
		}

		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return resp;
	}

	@Override
	public CapitalResponse getPayLoadTradingViewReal(TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalResponse response = null;
		ConfirmResponse resp = null;
		if (dto.getOrderFlow().equalsIgnoreCase("LONG")) {
			CapitalRequest request = Utils.createCapitalRequest(dto);
			response = capitalClient.createTrx(dto, request, Boolean.FALSE);
			resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.FALSE);

			Operations model = new Operations();
			model.setDealid(resp.getDealId());
			model.setDealreference(resp.getDealReference());
			model.setAffecteddeals(Utils.getAffectedDeals(resp.getAffectedDeals()));
			model.setCloseDate(null);
			model.setDirection(request.getDirection());
			model.setEpic(request.getEpic());
			model.setGuaranteedstop(request.getGuaranteedStop());
			model.setOpenDate(new Date());
			model.setOrderSize(request.getSize());
			model.setProfitlevel(
					Objects.nonNull(request.getProfitDistance()) ? new BigDecimal(request.getProfitDistance()) : null);
			model.setStoplevel(
					Objects.nonNull(request.getStopDistance()) ? new BigDecimal(request.getStopDistance()) : null);
			model.setState("OPEN");
			model.setAccount("REAL");

			operationsRepository.save(model);
		}

		if (dto.getOrderFlow().equalsIgnoreCase("CLOSE LONG")) {
			Operations model = operationsRepository.findByEpicAndStateAndAccountAndDirection(dto.getTicker(), "OPEN",
					"REAL", "BUY");
			capitalClient.delete(dto, model.getAffecteddeals(), Boolean.FALSE);
			model.setState("CLOSE");
			model.setCloseDate(new Date());
			operationsRepository.save(model);
		}

		if (dto.getOrderFlow().equalsIgnoreCase("SHORT")) {
			CapitalRequest request = Utils.createCapitalRequest(dto);
			response = capitalClient.createTrx(dto, request, Boolean.FALSE);
			resp = capitalClient.confirmTrx(dto, response.getDealReference(), Boolean.FALSE);

			Operations model = new Operations();
			model.setDealid(resp.getDealId());
			model.setDealreference(resp.getDealReference());
			model.setAffecteddeals(Utils.getAffectedDeals(resp.getAffectedDeals()));
			model.setCloseDate(null);
			model.setDirection(request.getDirection());
			model.setEpic(request.getEpic());
			model.setGuaranteedstop(request.getGuaranteedStop());
			model.setOpenDate(new Date());
			model.setOrderSize(request.getSize());
			model.setProfitlevel(
					Objects.nonNull(request.getProfitDistance()) ? new BigDecimal(request.getProfitDistance()) : null);
			model.setStoplevel(
					Objects.nonNull(request.getStopDistance()) ? new BigDecimal(request.getStopDistance()) : null);
			model.setState("OPEN");
			model.setAccount("REAL");

			operationsRepository.save(model);
		}

		if (dto.getOrderFlow().equalsIgnoreCase("CLOSE SHORT")) {
			Operations model = operationsRepository.findByEpicAndStateAndAccountAndDirection(dto.getTicker(), "OPEN",
					"REAL", "SELL");
			capitalClient.delete(dto, model.getAffecteddeals(), Boolean.FALSE);
			model.setState("CLOSE");
			model.setCloseDate(new Date());
			operationsRepository.save(model);
		}
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return response;
	}
}

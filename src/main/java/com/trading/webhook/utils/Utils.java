/**
 * 
 */
package com.trading.webhook.utils;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_ERROR;
import static com.trading.webhook.utils.ConstantUtil.LOG_REQUEST;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.webhook.dto.AffectedDealResponse;
import com.trading.webhook.dto.CapitalLoginResponse;
import com.trading.webhook.dto.CapitalRequest;
import com.trading.webhook.dto.TradingViewRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utils {
	
	@SuppressWarnings("unused")
	public static CapitalRequest createCapitalRequest(TradingViewRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalRequest result = new CapitalRequest();
		result.setEpic(dto.getTicker().toUpperCase());
		result.setDirection(dto.getOrderAction().toUpperCase());
		
		
		Double x = Double.parseDouble(dto.getOrderPrice());
		Double x15 = (x * 0.5 / 100);
		Double x2 = round((x * 2 / 100), 4);
		Double x3 = (x * 3 / 100);
		
		Double size = round((500 / x), 3);
		
		result.setSize(new BigDecimal(dto.getOrderContracts()));
		result.setGuaranteedStop(Boolean.FALSE);
		
		result.setStopDistance(null);
		result.setProfitDistance(null);
		
//		result.setLevel(Double.parseDouble(dto.getOrderPrice()));
//		result.setType("LIMIT");
//		result.setStopLevel(result.getLevel() - (result.getLevel() * 2 / 100));//calculate 2%
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			log.error(String.format(LOG_REQUEST, mapper.writeValueAsString(result)));
		} catch (JsonProcessingException e) {
			log.error(String.format(LOG_ERROR, Thread.currentThread().getStackTrace()[1].getMethodName()));
			e.printStackTrace();
		}
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return result;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static HttpHeaders getheaders(CapitalLoginResponse dto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-SECURITY-TOKEN", dto.getSecurityToken());
		headers.set("CST", dto.getCst());

		return headers;
	}
	
	public static String getAffectedDeals(ArrayList<AffectedDealResponse> affectedDeals) {
		StringBuffer joiner = new StringBuffer("");
	    for(int i = 0; i < affectedDeals.size(); i++) {
	    	if(i==0) {
	    		joiner.append(affectedDeals.get(i).getDealId());
	    	}else {
	    		joiner.append("," + affectedDeals.get(i).getDealId());
	    	}
	    }
	    String str = joiner.toString();

		return str;
	}
	
	
	
}

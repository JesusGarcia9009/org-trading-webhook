package com.trading.webhook.client;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_ERROR;
import static com.trading.webhook.utils.ConstantUtil.LOG_RESPONSE;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;
import static com.trading.webhook.utils.ConstantUtil.LOG_URL;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.webhook.dto.CapitalLoginRequest;
import com.trading.webhook.dto.CapitalLoginResponse;
import com.trading.webhook.dto.CapitalRequest;
import com.trading.webhook.dto.CapitalResponse;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.utils.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * TradingView Service implement
 * 
 * @author Jesus Garcia - Open2000
 * @version 0.1
 * @since jdk-11.0.7
 */
@Slf4j
@Component
public class CapitalClientImpl implements CapitalClient {

	/**
	 * RestTemplateMs restTemplateMs
	 */
	private RestTemplate restTemplate;
	private ObjectMapper mapper;
	private String capitalUrlDemo;
	private String capitalUrlReal;
	
    public CapitalClientImpl(RestTemplate restTemplate, 
    						 ObjectMapper mapper,
    						 @Value("${capital.com.demo.base.uri}") String capitalUrlDemo,
    						 @Value("${capital.com.real.base.uri}") String capitalUrlReal) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.capitalUrlDemo = capitalUrlDemo;
        this.capitalUrlReal = capitalUrlReal;
    }


	@Override
	public CapitalResponse createTrx(TradingViewRequest dto, CapitalRequest requestCapital, Boolean isDemo) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalLoginResponse login = getLoginEncrip(dto);
		HttpHeaders headers = Utils.getheaders(login);
		//CapitalRequest requestCapital = Utils.createCapitalRequest(dto);
		HttpEntity<Object> request = new HttpEntity<>(requestCapital, headers);
		String url = (isDemo ? capitalUrlDemo : capitalUrlReal ) + "api/v1/positions";
		log.info(String.format(LOG_URL, url));
		ResponseEntity<CapitalResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, CapitalResponse.class);

		CapitalResponse responseBody = response.getBody();
		try {
			log.info(String.format(LOG_RESPONSE, mapper.writeValueAsString(responseBody)));
		} catch (JsonProcessingException e) {
			log.error(String.format(LOG_ERROR, Thread.currentThread().getStackTrace()[1].getMethodName()));
		}

		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return responseBody;
	}
	
	@Override
	public CapitalResponse delete(TradingViewRequest dto,String affectedDeals, Boolean isDemo) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalLoginResponse login = getLoginEncrip(dto);
		HttpHeaders headers = Utils.getheaders(login);
		HttpEntity<Object> request = new HttpEntity<>(headers);
		CapitalResponse responseBody = null;
		
		List<String> list = Arrays.asList(affectedDeals.replace("[", "").replace("]", "").split(","));
		for (String item : list) {
			String path = (isDemo ? capitalUrlDemo : capitalUrlReal ) + "api/v1/positions/"+ item;
			log.info(String.format(LOG_URL, path));
			ResponseEntity<CapitalResponse> response = restTemplate.exchange(path, HttpMethod.DELETE, request, CapitalResponse.class);

			responseBody = response.getBody();
			try {
				log.info(String.format(LOG_RESPONSE, mapper.writeValueAsString(responseBody)));
			} catch (JsonProcessingException e) {
				log.error(String.format(LOG_ERROR, Thread.currentThread().getStackTrace()[1].getMethodName()));
			}
		}

		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return responseBody;
	}
	
	@Override
	public ConfirmResponse confirmTrx(TradingViewRequest dto, String capitalId, Boolean isDemo) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		CapitalLoginResponse login = getLoginEncrip(dto);
		HttpHeaders headers = Utils.getheaders(login);
		HttpEntity<Object> request = new HttpEntity<>(headers);
		String path = (isDemo ? capitalUrlDemo : capitalUrlReal ) + "api/v1/confirms/"+ capitalId;
		log.info(String.format(LOG_URL, path));
		ResponseEntity<ConfirmResponse> response = restTemplate.exchange(path, HttpMethod.GET, request, ConfirmResponse.class);

		ConfirmResponse responseBody = response.getBody();
		try {
			log.info(String.format(LOG_RESPONSE, mapper.writeValueAsString(responseBody)));
		} catch (JsonProcessingException e) {
			log.error(String.format(LOG_ERROR, Thread.currentThread().getStackTrace()[1].getMethodName()));
		}

		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return responseBody;
	}

	
	@Override
	public CapitalLoginResponse getLoginEncrip(TradingViewRequest dto) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-CAP-API-KEY", dto.getBrokerApiKey());		
		
		HttpEntity<Object> request = new HttpEntity<>(new CapitalLoginRequest(dto.getBrokerEmail(), dto.getBrokerPass(), Boolean.FALSE),headers);
		ResponseEntity<CapitalLoginResponse> response = restTemplate.exchange(capitalUrlDemo + "api/v1/session", HttpMethod.POST,
				request, CapitalLoginResponse.class);

		CapitalLoginResponse responseBody = response.getBody();
		HttpHeaders headersResponse = response.getHeaders();
		String cst = headersResponse.get("CST").get(0);
		String securityToken = headersResponse.get("X-SECURITY-TOKEN").get(0);
		responseBody.setCst(cst);
		responseBody.setSecurityToken(securityToken);

		return responseBody;
	}



}

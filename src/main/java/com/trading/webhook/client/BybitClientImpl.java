package com.trading.webhook.client;

import static com.trading.webhook.utils.ConstantUtil.LOG_END;
import static com.trading.webhook.utils.ConstantUtil.LOG_START;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.trading.webhook.dto.BybitRequest;
import com.trading.webhook.dto.BybitResponse;

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
public class BybitClientImpl implements BybitClient {

	/**
	 * RestTemplateMs restTemplateMs
	 */
	@Autowired
	private RestTemplate restTemplate;

	@Value("${bybit.demo.base.uri}")
	private String bybitUrlDemo;

	@Value("${bybit.real.base.uri}")
	private String bybitUrlReal;

//	final static String API_KEY = "XXXXXXXXXX";
//	final static String API_SECRET = "XXXXXXXXXX";
//	final static String TIMESTAMP = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
	final static String RECV_WINDOW = "50000";

	@Override
	public BybitResponse createTrxDemo(BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));

		 Map<String, Object> map = new HashMap<>();
	        map.put("category","spot");
	        map.put("symbol", "BTCUSDT");
	        map.put("side", "Buy");
	        map.put("orderType", "Market");
	        map.put("qty", 200);
	        map.put("timeInForce", "IOC");
	        map.put("orderLinkId", "spot-test-04");
	        map.put("isLeverage", 0);
	        map.put("orderFilter", "Order");
	        
	        
//	        {"category":"spot","symbol":"BTCUSDT","side":"Buy","orderType":"Market","qty":"200","timeInForce":"IOC","orderLinkId":"spot-test-04","isLeverage":0,"orderFilter":"Order"}
	        
		String signature;
		try {
			signature = genGetSign(map, dto.getApiKey(), dto.getApiSecretKey());

			StringBuilder sb = genQueryStr(map);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("X-BAPI-API-KEY", dto.getApiKey());
			headers.add("X-BAPI-SIGN", signature);
			headers.add("X-BAPI-SIGN-TYPE", "2");
			headers.add("X-BAPI-TIMESTAMP", Long.toString(ZonedDateTime.now().toInstant().toEpochMilli()));
			headers.add("X-BAPI-RECV-WINDOW", RECV_WINDOW);
			HttpEntity<Object> request = new HttpEntity<>(map, headers);
			ResponseEntity<String> response = restTemplate.exchange(
					"https://api-testnet.bybit.com/v5/order/create", HttpMethod.POST, request, String.class);

			response.getBody();

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

//        HttpClient client = new HttpClient().newBuilder().build();
//        Request request = new Request.Builder()
//                .url("https://api-testnet.bybit.com/v5/order/realtime?" + sb)
//                .get()
//                .addHeader("X-BAPI-API-KEY", API_KEY)
//                .addHeader("X-BAPI-SIGN", signature)
//                .addHeader("X-BAPI-SIGN-TYPE", "2")
//                .addHeader("X-BAPI-TIMESTAMP", TIMESTAMP)
//                .addHeader("X-BAPI-RECV-WINDOW", RECV_WINDOW)
//                .build();
//        Call call = client.newCall(request);
//        try {
//            Response response = call.execute();
//            assert response.body() != null;
//            System.out.println(response.body().string());
//        }catch (IOException e){
//            e.printStackTrace();
//        }
		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return null;
	}

	@Override
	public BybitResponse createTrxReal(BybitRequest dto) {
		log.info(String.format(LOG_START, Thread.currentThread().getStackTrace()[1].getMethodName()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", token);
		HttpEntity<Object> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(bybitUrlReal, HttpMethod.GET, request, String.class);

		response.getBody();

		log.info(String.format(LOG_END, Thread.currentThread().getStackTrace()[1].getMethodName()));
		return null;
	}

	private static String genGetSign(Map<String, Object> params, String API_KEY, String API_SECRET) throws NoSuchAlgorithmException, InvalidKeyException {
		StringBuilder sb = genQueryStr(params);
		String queryStr = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli()) + API_KEY + RECV_WINDOW + sb;

		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
	}

	private static StringBuilder genQueryStr(Map<String, Object> map) {
		Set<String> keySet = map.keySet();
		Iterator<String> iter = keySet.iterator();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			String key = iter.next();
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	private static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}

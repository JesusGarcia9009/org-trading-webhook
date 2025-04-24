package com.trading.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
	
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
	
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return new RestTemplate();
	}
	
	private static final String TOKEN_URL = "https://login.sae1.pure.cloud/oauth/token";
    private static final String API_URL = "https://api.sae1.pure.cloud/api/v2/analytics/conversations/details/query";
    private static final String CLIENT_ID = "8a828ed8-47c0-41a5-a870-d23a0dc7025d";
    private static final String CLIENT_SECRET = "FaKswcjxmkGyaIdogiPKTRmWYP0ymWCGBY1qnXEJ13Q";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Get access token
        String accessToken = getAccessToken(restTemplate);

        if (accessToken != null) {
            // Step 2: Fetch all conversations
            List<Map<String, Object>> allConversations = fetchConversations(restTemplate, accessToken);
            System.out.println("Total conversations fetched: " + allConversations.size());
            
            
            for (Map<String, Object> map : allConversations) {
				if(map.get("conversationId").equals("263c8256-1621-41a4-a3fd-1a4ff7140c37")){
					System.out.println("Encontrado");
				}
			}
            
        } else {
            System.err.println("Failed to fetch access token.");
        }
    }

    private static String getAccessToken(RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = UriComponentsBuilder.newInstance()
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_id", CLIENT_ID)
                .queryParam("client_secret", CLIENT_SECRET)
                .build()
                .toUriString().substring(1); // Remove leading '?' from the URI

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return (String) responseBody.get("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Map<String, Object>> fetchConversations(RestTemplate restTemplate, String accessToken) {
        List<Map<String, Object>> allConversations = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);

        int pageNumber = 1;
        boolean moreData = true;

        while (moreData) {
            String requestBody = String.format(
                    "{\"interval\":\"2024-12-16T00:00:00.000Z/2024-12-16T23:59:00.000Z\",\"paging\":{\"pageSize\":100,\"pageNumber\":%d}}",
                    pageNumber);

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            try {
                ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, Map.class);
                Map<String, Object> responseBody = response.getBody();

                if (responseBody != null && responseBody.containsKey("conversations")) {
                    List<Map<String, Object>> conversations = (List<Map<String, Object>>) responseBody.get("conversations");
                    allConversations.addAll(conversations);

                    // Check if there are more pages
                    int totalHits = (int) responseBody.getOrDefault("totalHits", 0);
                    moreData = allConversations.size() < totalHits;
                } else {
                    moreData = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                moreData = false;
            }

            pageNumber++;
        }

        return allConversations;
    }

}

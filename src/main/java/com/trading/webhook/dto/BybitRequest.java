package com.trading.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class BybitRequest {
	
	@JsonProperty("broker_email")
	public String brokerEmail;
	
	@JsonProperty("api_key")
    public String apiKey;
	
	@JsonProperty("api_secret_key")
    public String apiSecretKey;
	
	@JsonProperty("real_order")
    public String realOrder;
	
	@JsonProperty("order_type")
    public String orderType;
	
	@JsonProperty("order_market")
    public String orderMarket;
	
	@JsonProperty("exchange")
    public String exchange;
	
	@JsonProperty("ticker")
    public String ticker;
	
	@JsonProperty("time")
    public String time;
	
	@JsonProperty("order_contracts")
    public String orderContracts;
    
    @JsonProperty("order_action")
    public String orderAction;
    
    @JsonProperty("order_price")
    public String orderPrice;
    
    @JsonProperty("order_flow")
    public String orderFlow;

}

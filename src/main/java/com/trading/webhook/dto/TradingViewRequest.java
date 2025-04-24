package com.trading.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class TradingViewRequest {
	
	@JsonProperty("broker_email")
	public String brokerEmail;
	
	@JsonProperty("broker_api_key")
    public String brokerApiKey;
	
	@JsonProperty("broker_pass")
    public String brokerPass;
	
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

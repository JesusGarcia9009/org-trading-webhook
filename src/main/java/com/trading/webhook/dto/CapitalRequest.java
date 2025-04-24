package com.trading.webhook.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class CapitalRequest {
	
	@JsonProperty
	public String epic;
	
	@JsonProperty
    public String direction;
	
	@JsonProperty
    public BigDecimal size;
	
	@JsonProperty
    public Boolean guaranteedStop;
	
	@JsonProperty
    public Double stopDistance;
	
	@JsonProperty
    public Double profitDistance;
	
}

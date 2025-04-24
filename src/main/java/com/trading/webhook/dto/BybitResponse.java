package com.trading.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class BybitResponse {
	
	@JsonProperty
	private Long id;
	
	@JsonProperty
	private String name;

	@JsonProperty
	private Long idEstablishment;
	
	@JsonProperty
	private String nameEstablishment;

}

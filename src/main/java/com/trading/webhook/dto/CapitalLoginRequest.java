package com.trading.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class CapitalLoginRequest {
	
	@JsonProperty
    public String identifier ;
	
	@JsonProperty
    public String password;
	
	@JsonProperty
    public Boolean encryptedPassword;

}

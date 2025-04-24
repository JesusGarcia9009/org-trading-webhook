package com.trading.webhook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountResponse {
	
    public long accountId;
    public String accountName;
    public String preferred;
    public String accountType;
	
	
}

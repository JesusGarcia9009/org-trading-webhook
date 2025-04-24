package com.trading.webhook.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class CapitalLoginResponse {
	
	public String accountType;
    public AccountInfoResponse accountInfo;
    public String currencyIsoCode;
    public String currencySymbol;
    public long currentAccountId;
    public String streamingHost;
    public ArrayList<AccountResponse> accounts;
    public int clientId;
    public int timezoneOffset;
    public String hasActiveDemoAccounts;
    public String hasActiveLiveAccounts;
    public String trailingStopsEnabled;
    public String cst ;
    public String SecurityToken;

}

package com.trading.webhook.dto;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class ConfirmResponse {
	
	public Date date;
    public String status;
    public String reason;
    public String dealStatus;
    public String epic;
    public String dealReference;
    public String dealId;
    public ArrayList<AffectedDealResponse> affectedDeals;
    public double level;
    public double size;
    public String direction;
    public double stopLevel;
    public double profitLevel;
    public boolean guaranteedStop;
    public boolean trailingStop;
	
	
}

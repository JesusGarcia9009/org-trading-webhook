package com.trading.webhook.service;

import com.trading.webhook.dto.TradingViewRequest;
import com.trading.webhook.dto.ConfirmResponse;
import com.trading.webhook.dto.CapitalResponse;

public interface CapitalService {
    ConfirmResponse closeOrder(String symbol, String sl, String tp, String accountType);
    CapitalResponse openLongOrder(String symbol, String sl, String tp, String accountType);
    CapitalResponse openShortOrder(String symbol, String sl, String tp, String accountType);
}
package com.trading.webhook.service;

import com.trading.webhook.entity.Operations;

public interface OperationService {
    void createOrUpdateOperation(String accountType, String cmd, String currency, String symbol, Double tp, Double sl);
    void closeOperation(String symbol, String accountType);
}
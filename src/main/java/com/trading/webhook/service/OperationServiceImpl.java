package com.trading.webhook.service;

import com.trading.webhook.entity.Operations;
import com.trading.webhook.dto.CapitalRequest;
import com.trading.webhook.repository.OperationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationsRepository operationsRepository;
    @Override
    public void createOrUpdateOperation(String accountType, String cmd, String currency, String symbol, Double tp, Double sl) {
        Operations operations = Operations.builder()
                .accountType(accountType)
                .cmd(cmd)
                .currency(currency)
                .symbol(symbol)
                .tp(tp)
                .sl(sl)
                .serverTime(LocalDateTime.now())
                .build();
        operationsRepository.save(operations);
    }

    @Override
    public void closeOperation(String symbol, String accountType) {
        Operations operation = operationsRepository.findBySymbolAndStateAndAccountType(symbol, "OPEN", accountType);
        if (Objects.nonNull(operation)) {
            operation.setState("CLOSE");
            operation.setCloseDate(new Date());
            operationsRepository.save(operation);
        }
    }

}
package com.trading.webhook.service;
import com.trading.webhook.client.CapitalClientImpl;
import com.trading.webhook.client.CapitalClient;
import com.trading.webhook.dto.*;
import com.trading.webhook.entity.Operations;
import com.trading.webhook.repository.OperationsRepository;
import com.trading.webhook.utils.ConstantUtil;
import com.trading.webhook.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class CapitalServiceImpl implements CapitalService {

    private final CapitalClient capitalClient;
    private final OperationService operationService;
    private final OperationsRepository operationsRepository;
    private static final Logger logger = LoggerFactory.getLogger(CapitalServiceImpl.class);


    public TradingViewResponse closeOrder(String symbol, String sl, String tp, String accountType) {
        try {
            CapitalRequest request = new CapitalRequest();
            request.setCmd("CLOSE");
            request.setSymbol(symbol.toUpperCase());
            if(Objects.nonNull(sl)){
                request.setSl(Double.valueOf(sl));
            }
            if(Objects.nonNull(tp)){
                request.setTp(Double.valueOf(tp));
            }
            request.setAccountType(accountType);
            String[] symbolParts = symbol.split("/");
            if(symbolParts.length>0){
                request.setCurrency(symbolParts[1]);
            }
            CapitalResponse response = capitalClient.sendOrder(request);
            operationService.closeOperation(symbol, accountType);
            if (Objects.isNull(response) || !Objects.equals(response.getReturnCode(), "0")) {
                logger.error("Capital close order error: {}", response);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Capital close order error");
            }
            operationService.createOrUpdateOperation(request.getAccountType(), request.getCmd(), request.getCurrency(), request.getSymbol(), request.getTp(), request.getSl());
            return TradingViewResponse.builder()
                    .serverTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .message(response.getReturnMsg())
                    .build();
        } catch (Exception e) {
            logger.error("Error close order", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error closing order");
        }
    }

    public TradingViewResponse openLongOrder(String symbol, String sl, String tp, String accountType) {
        try {
            CapitalRequest request = new CapitalRequest();
            request.setCmd("OPEN_LONG");
            request.setSymbol(symbol.toUpperCase());
            if(Objects.nonNull(sl)){
                request.setSl(Double.valueOf(sl));
            }
            if(Objects.nonNull(tp)){
                request.setTp(Double.valueOf(tp));
            }
            request.setAccountType(accountType);
            String[] symbolParts = symbol.split("/");
            if(symbolParts.length>0){
                request.setCurrency(symbolParts[1]);
            }
            CapitalResponse response = capitalClient.sendOrder(request);

            if (Objects.isNull(response) || !Objects.equals(response.getReturnCode(), "0")) {
                logger.error("Capital open long order error: {}", response);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Capital open long order error");
            }
            operationService.createOrUpdateOperation(request.getAccountType(), request.getCmd(), request.getCurrency(), request.getSymbol(), request.getTp(), request.getSl());

            return TradingViewResponse.builder()
                    .serverTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .message(response.getReturnMsg())
                    .build();
        } catch (Exception e) {
            logger.error("Error open long order", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error opening long order");
        }
    }

    public TradingViewResponse openShortOrder(String symbol, String sl, String tp, String accountType) {
        try {
            CapitalRequest request = new CapitalRequest();
            request.setCmd("OPEN_SHORT");
            request.setSymbol(symbol.toUpperCase());
            if(Objects.nonNull(sl)){
                request.setSl(Double.valueOf(sl));
            }
            if(Objects.nonNull(tp)){
                request.setTp(Double.valueOf(tp));
            }
            request.setAccountType(accountType);
            String[] symbolParts = symbol.split("/");
            if(symbolParts.length>0){
                request.setCurrency(symbolParts[1]);
            }
            CapitalResponse response = capitalClient.sendOrder(request);
            if (Objects.isNull(response) || !Objects.equals(response.getReturnCode(), "0")) {
                logger.error("Capital open short order error: {}", response);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Capital open short order error");
            }
            operationService.createOrUpdateOperation(request.getAccountType(), request.getCmd(), request.getCurrency(), request.getSymbol(), request.getTp(), request.getSl());
            return TradingViewResponse.builder()
                    .serverTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .message(response.getReturnMsg())
                    .build();
        } catch (Exception e) {
            logger.error("Error open short order", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error opening short order");
        }
    }
}
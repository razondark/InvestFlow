package com.mvp.investservice.web.mapper;

import com.mvp.investservice.util.MoneyParser;
import com.mvp.investservice.web.dto.Operation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class OperationMapper {


    public Operation toDto(ru.tinkoff.piapi.contract.v1.Operation operation, String assetName, int lot) {
        Operation operationResponse = new Operation();

        operationResponse.setOperationId(operation.getId());
        operationResponse.setOperationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(operation.getDate().getSeconds(), operation.getDate().getNanos()), ZoneId.systemDefault()));
        operationResponse.setOperationState(operation.getState().name());
        operationResponse.setCurrency(operation.getCurrency());
        operationResponse.setFigi(operation.getFigi());
        operationResponse.setAssetName(assetName);
        operationResponse.setInstrumentType(operation.getInstrumentType());
        operationResponse.setPayment(MoneyParser.moneyValueToBigDecimal(operation.getPayment()));
        operationResponse.setOperationType(operation.getOperationType().name());
        operationResponse.setInstrumentPrice(MoneyParser.moneyValueToBigDecimal(operation.getPrice()));
        operationResponse.setLotPrice(operationResponse.getInstrumentPrice().multiply(BigDecimal.valueOf(lot)));

        if (operationResponse.getInstrumentPrice().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal quantity = operationResponse.getPayment()
                    .divide(BigDecimal.valueOf(-1)
                            .multiply(operationResponse.getInstrumentPrice()));

            operationResponse.setQuantity(quantity.intValue());
        }

        return operationResponse;
    }


}

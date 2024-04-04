package com.mvp.investservice.service.impl;

import com.mvp.investservice.domain.exception.CannotProceedApiRequestException;
import com.mvp.investservice.service.OperationService;
import com.mvp.investservice.web.dto.AccountDto;
import com.mvp.investservice.web.dto.Operation;
import com.mvp.investservice.web.dto.OperationResponse;
import com.mvp.investservice.web.mapper.OperationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.core.InvestApi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final InvestApi investApi;

    private final OperationMapper operationMapper;

    @Override
    public OperationResponse getAllOperations(AccountDto accountDto) {
        List<ru.tinkoff.piapi.contract.v1.Operation> allOperations = new ArrayList<>();
        try {

            allOperations = investApi.getOperationsService()
                    .getAllOperationsSync(accountDto.getInvestAccountId(), Instant.now().minus(3, ChronoUnit.DAYS), Instant.now());
        } catch (Exception e) {
            throw new CannotProceedApiRequestException("Ошибка получения операций по счёту.");
        }

        List<Operation> operations = new ArrayList<>();
        for (ru.tinkoff.piapi.contract.v1.Operation operation : allOperations) {
            Instrument asset = investApi.getInstrumentsService()
                    .getInstrumentByFigiSync(operation.getFigi());
            String assetName = asset.getName();
            int lot = asset.getLot();
            operations.add(operationMapper.toDto(operation, assetName, lot));
        }

        OperationResponse operationResponse = new OperationResponse();
        operationResponse.setOperations(operations);
      
        return operationResponse;
    }
}

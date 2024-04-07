package com.mvp.investservice.service;

import com.mvp.investservice.web.dto.AccountDto;
import com.mvp.investservice.web.dto.Operation;
import com.mvp.investservice.web.dto.OperationResponse;

public interface OperationService {

    OperationResponse getAllOperations(AccountDto accountDto);
}

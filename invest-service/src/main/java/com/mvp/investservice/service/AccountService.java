package com.mvp.investservice.service;

import com.mvp.investservice.web.dto.BalanceDto;
import com.mvp.investservice.web.dto.PayInDto;
import com.mvp.investservice.web.dto.AccountDto;
import ru.tinkoff.piapi.contract.v1.MoneyValue;

import java.math.BigDecimal;

public interface AccountService {

    AccountDto openAccount(Long userId);

    AccountDto getAccount();

    BalanceDto payIn(PayInDto payInDto);

    BigDecimal getBalance(String accountId);
}

package com.mvp.investservice.service.impl;

import com.mvp.investservice.domain.exception.ResourceNotFoundException;
import com.mvp.investservice.service.AccountService;
import com.mvp.investservice.service.props.LinkProperties;
import com.mvp.investservice.util.MoneyParser;
import com.mvp.investservice.web.dto.AccountDto;
import com.mvp.investservice.web.dto.BalanceDto;
import com.mvp.investservice.web.dto.PayInDto;
import com.mvp.investservice.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final InvestApi investApi;

    private final RestTemplate restTemplate;

    private final LinkProperties linkProperties;

    @Override
    public AccountDto openAccount(Long userId) {
        String accountId = investApi.getSandboxService()
                .openAccountSync();

        UserDto userDto = new UserDto();
        userDto.setId(userId);

        AccountDto accountDto = new AccountDto();
        accountDto.setInvestAccountId(accountId);
        accountDto.setUserDto(userDto);

        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto);
        ResponseEntity<AccountDto> response
                = restTemplate.exchange(linkProperties.getCreateAccountLink(), HttpMethod.POST, request, AccountDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ResourceNotFoundException("Account has not been created");
        }
    }

    @Override
    public BalanceDto payIn(PayInDto payInDto) {
        MoneyValue moneyValue = getMoneyValue(payInDto);
        MoneyValue balance
                = investApi.getSandboxService().payInSync(payInDto.getAccountId(), moneyValue);
        BigDecimal newBalance
                = MoneyParser.moneyValueToBigDecimal(balance);

        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setAddedMoney(payInDto.getMoneyToPay());
        balanceDto.setBalance(newBalance);
        return balanceDto;
    }

    // return RUB balance
    @Override
    public BigDecimal getBalance(String accountId) {
        var balance = investApi.getSandboxService().payInSync(accountId, MoneyValue.newBuilder().build());

        return MoneyParser.moneyValueToBigDecimal(balance);
    }

    @Override // TODO: change
    public AccountDto getAccount() {
        Account account = investApi.getSandboxService().getAccountsSync().get(0);
        AccountDto accountDto = new AccountDto();
        accountDto.setInvestAccountId(account.getId());

        return accountDto;
    }

    private MoneyValue getMoneyValue(PayInDto payInDto) {
        BigDecimal money = payInDto.getMoneyToPay();
        MoneyValue moneyValue = MoneyValue.newBuilder()
                .setCurrency("rub")
                .setUnits(money != null ? money.longValue() : 0)
                .setNano(money != null ? money.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(1000000000)).intValue() : 0)
                .build();
        return moneyValue;
    }
}

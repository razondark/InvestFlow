package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.repository.AccountRepository;
import com.mvp.crudmicroservice.user.repository.UserRepository;
import com.mvp.crudmicroservice.user.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        Long userId = account.getUser().getId();
        Optional<Account> existAccount
                = accountRepository.findByUser_Id(userId);

        if (existAccount.isEmpty()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("There is no user with id " + userId));
            account.setUser(user);
            return accountRepository.save(account);
        } else {
            return existAccount.get();
        }
    }

    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    @Override
    public Account getByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no user with id " + userId));
        Account account = new Account();
        account.setInvestAccountId("a7c911bb-5b01-41bf-9db7-3767ac46385d");
        account.setUser(user);

        return account;
    }
}

package com.mvp.crudmicroservice.user.service;

import com.mvp.crudmicroservice.user.domain.user.Account;

public interface AccountService {

    Account create(Account account);

    Account update(Account account);

    Account getById(String accountId);

    Account getByUserId(Long userId);
}

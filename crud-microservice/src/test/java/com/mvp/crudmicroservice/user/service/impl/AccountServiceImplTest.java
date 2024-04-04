package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.repository.AccountRepository;
import com.mvp.crudmicroservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_NewAccount_ReturnsCreatedAccount() {

        User user = new User();
        Account account = new Account();
        account.setUser(user);
        when(accountRepository.findByUser_Id(any())).thenReturn(Optional.empty());
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(accountRepository.save(any())).thenReturn(account);


        Account createdAccount = accountService.create(account);


        assertNotNull(createdAccount);
        assertEquals(account.getInvestAccountId(), createdAccount.getInvestAccountId());
        assertEquals(account.getUser(), createdAccount.getUser());
    }

    @Test
    void create_ExistingAccount_ReturnsExistingAccount() {

        User user = new User();
        Account account = new Account();
        account.setUser(user);
        when(accountRepository.findByUser_Id(any())).thenReturn(Optional.of(account));


        Account existingAccount = accountService.create(account);


        assertNotNull(existingAccount);
        assertEquals(account.getInvestAccountId(), existingAccount.getInvestAccountId());
        assertEquals(account.getUser(), existingAccount.getUser());
    }

    @Test
    void update_Account_ReturnsUpdatedAccount() {

        Account account = new Account();
        when(accountRepository.save(any())).thenReturn(account);


        Account updatedAccount = accountService.update(account);


        assertNotNull(updatedAccount);
        assertEquals(account, updatedAccount);
    }

    @Test
    void getById_ExistingAccountId_ReturnsAccount() {

        String accountId = "existingAccountId";
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));


        Account retrievedAccount = accountService.getById(accountId);


        assertNotNull(retrievedAccount);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void getById_NonExistingAccountId_ThrowsResourceNotFoundException() {

        String nonExistingAccountId = "nonExistingAccountId";
        when(accountRepository.findById(nonExistingAccountId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> accountService.getById(nonExistingAccountId));
    }

    @Test
    void getByUserId_ExistingUserId_ReturnsAccount() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Account account = new Account();
        account.setUser(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        Account retrievedAccount = accountService.getByUserId(userId);


        assertNotNull(retrievedAccount);
        assertEquals(user, retrievedAccount.getUser());
    }

    @Test
    void getByUserId_NonExistingUserId_ThrowsResourceNotFoundException() {

        Long nonExistingUserId = 1L;
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> accountService.getByUserId(nonExistingUserId));
    }
    @Test
    void testGetAccountById_Successful() {

        String accountId = "1";
        Account account = new Account();
        when(accountRepository.findById(any(String.class))).thenReturn(Optional.of(account));


        Account retrievedAccount = accountService.getById(accountId);


        assertNotNull(retrievedAccount);
        verify(accountRepository, times(1)).findById(any(String.class));
    }

    @Test
    void testGetAccountByUserId_Successful() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));


        Account account = accountService.getByUserId(userId);


        assertNotNull(account);
        assertEquals(user, account.getUser());
    }
}

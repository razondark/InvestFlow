package com.mvp.crudmicroservice.user.web.controller;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.service.AccountService;
import com.mvp.crudmicroservice.user.web.dto.user.AccountDto;
import com.mvp.crudmicroservice.user.web.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountController accountController;

    public AccountControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountByUserId_UserExists_ReturnsAccountDto() {
        // Arrange
        long userId = 1L;
        Account account = new Account();
        AccountDto accountDto = new AccountDto();
        when(accountService.getByUserId(userId)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        // Act
        ResponseEntity<AccountDto> response = accountController.getAccountByUserId(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }

    @Test
    void testGetAccountByUserId_UserDoesNotExist_ReturnsErrorResponse() {
        // Arrange
        long userId = 1L;
        ResourceNotFoundException exception = new ResourceNotFoundException("User not found");
        when(accountService.getByUserId(userId)).thenThrow(exception);

        // Act
        ResponseEntity<Object> response = accountController.getAccountByUserId(userId);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User not found", response.getBody());
        assertEquals("User not found", response.getHeaders().getFirst("error-message"));
    }
    @Test
    void testUpdate_ValidAccount_ReturnsUpdatedAccountDto() {
        // Arrange
        AccountDto accountDto = new AccountDto();
        Account account = new Account();
        when(accountMapper.toEntity(accountDto)).thenReturn(account);
        when(accountService.update(account)).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(accountDto);

        // Act
        ResponseEntity<AccountDto> response = accountController.update(accountDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
    }
}

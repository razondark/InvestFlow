package com.mvp.crudmicroservice.user.web.dto.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountDtoTest {

    @Test
    public void testInvestAccountId() {
        AccountDto accountDto = new AccountDto();
        accountDto.setInvestAccountId("123456");
        assertEquals("123456", accountDto.getInvestAccountId());
    }

    @Test
    public void testUserDto() {
        AccountDto accountDto = new AccountDto();
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        accountDto.setUserDto(userDto);
        assertEquals("testuser", accountDto.getUserDto().getUsername());
    }

    @Test
    public void testTelegramDto() {
        AccountDto accountDto = new AccountDto();
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setTelegramId("12345");
        accountDto.setTelegramDto(telegramDto);
        assertEquals("12345", accountDto.getTelegramDto().getTelegramId());
    }

    @Test
    public void testInvestAccountIdNull() {
        AccountDto accountDto = new AccountDto();
        assertNull(accountDto.getInvestAccountId());
    }

    @Test
    public void testUserDtoNull() {
        AccountDto accountDto = new AccountDto();
        assertNull(accountDto.getUserDto());
    }

    @Test
    public void testTelegramDtoNull() {
        AccountDto accountDto = new AccountDto();
        assertNull(accountDto.getTelegramDto());
    }

    @Test
    public void testInvestAccountIdSetter() {
        AccountDto accountDto = new AccountDto();
        accountDto.setInvestAccountId("123456");
        assertEquals("123456", accountDto.getInvestAccountId());
    }

    @Test
    public void testUserDtoSetter() {
        AccountDto accountDto = new AccountDto();
        UserDto userDto = new UserDto();
        accountDto.setUserDto(userDto);
        assertEquals(userDto, accountDto.getUserDto());
    }

    @Test
    public void testTelegramDtoSetter() {
        AccountDto accountDto = new AccountDto();
        TelegramDto telegramDto = new TelegramDto();
        accountDto.setTelegramDto(telegramDto);
        assertEquals(telegramDto, accountDto.getTelegramDto());
    }
}

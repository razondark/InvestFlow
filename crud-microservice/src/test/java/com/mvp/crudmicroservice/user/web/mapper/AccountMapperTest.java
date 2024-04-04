package com.mvp.crudmicroservice.user.web.mapper;

import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.web.dto.user.AccountDto;
import com.mvp.crudmicroservice.user.web.dto.user.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    @Test
    void testToDto() {
        AccountMapper accountMapper = new AccountMapperImpl();

        User user = new User();
        user.setId(1L);
        user.setName("Vera");
        user.setUsername("vera123");
        user.setPassword("password");

        Account account = new Account();
        account.setInvestAccountId("123456");
        account.setUser(user);

        AccountDto accountDto = accountMapper.toDto(account);

        assertEquals(account.getInvestAccountId(), accountDto.getInvestAccountId());
        assertEquals(user.getId(), accountDto.getUserDto().getId());
        assertEquals(user.getName(), accountDto.getUserDto().getName());
        assertEquals(user.getUsername(), accountDto.getUserDto().getUsername());
        assertEquals(user.getPassword(), accountDto.getUserDto().getPassword());
    }

    @Test
    void testToEntity() {
        AccountMapper accountMapper = new AccountMapperImpl();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Vera");
        userDto.setUsername("vera123");
        userDto.setPassword("password");

        AccountDto accountDto = new AccountDto();
        accountDto.setInvestAccountId("123456");
        accountDto.setUserDto(userDto);

        Account account = accountMapper.toEntity(accountDto);

        assertEquals(accountDto.getInvestAccountId(), account.getInvestAccountId());
        assertEquals(userDto.getId(), account.getUser().getId());
        assertEquals(userDto.getName(), account.getUser().getName());
        assertEquals(userDto.getUsername(), account.getUser().getUsername());
        assertEquals(userDto.getPassword(), account.getUser().getPassword());
    }
}

package com.mvp.crudmicroservice.user.web.dto.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TelegramDtoTest {

    @Test
    public void testId() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setId(1L);
        assertEquals(1L, telegramDto.getId());
    }

    @Test
    public void testName() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setName("TestName");
        assertEquals("TestName", telegramDto.getName());
    }

    @Test
    public void testTelegramId() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setTelegramId("12345");
        assertEquals("12345", telegramDto.getTelegramId());
    }

    @Test
    public void testIdNull() {
        TelegramDto telegramDto = new TelegramDto();
        assertNull(telegramDto.getId());
    }

    @Test
    public void testNameNull() {
        TelegramDto telegramDto = new TelegramDto();
        assertNull(telegramDto.getName());
    }

    @Test
    public void testTelegramIdNull() {
        TelegramDto telegramDto = new TelegramDto();
        assertNull(telegramDto.getTelegramId());
    }

    @Test
    public void testIdSetter() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setId(1L);
        assertEquals(1L, telegramDto.getId());
    }

    @Test
    public void testNameSetter() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setName("TestName");
        assertEquals("TestName", telegramDto.getName());
    }

    @Test
    public void testTelegramIdSetter() {
        TelegramDto telegramDto = new TelegramDto();
        telegramDto.setTelegramId("12345");
        assertEquals("12345", telegramDto.getTelegramId());
    }
}

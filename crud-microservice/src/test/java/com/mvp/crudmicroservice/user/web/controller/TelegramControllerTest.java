package com.mvp.crudmicroservice.user.web.controller;

import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.Telegram;
import com.mvp.crudmicroservice.user.service.TelegramService;
import com.mvp.crudmicroservice.user.web.dto.user.TelegramDto;
import com.mvp.crudmicroservice.user.web.mapper.TelegramMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TelegramControllerTest {

    @Mock
    private TelegramService telegramService;

    @Mock
    private TelegramMapper telegramMapper;

    @InjectMocks
    private TelegramController telegramController;

    public TelegramControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTelegram_ValidTelegram_ReturnsCreatedTelegramDto() {
        // Arrange
        TelegramDto telegramDto = new TelegramDto();
        Telegram telegram = new Telegram();
        when(telegramMapper.toEntity(telegramDto)).thenReturn(telegram);
        when(telegramService.create(telegram)).thenReturn(telegram);
        when(telegramMapper.toDto(telegram)).thenReturn(telegramDto);

        // Act
        ResponseEntity<TelegramDto> response = telegramController.createTelegram(telegramDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(telegramDto, response.getBody());
    }

    @Test
    void testCreateTelegram_TelegramAlreadyExists_ReturnsConflictStatus() {
        // Arrange
        TelegramDto telegramDto = new TelegramDto();
        Telegram telegram = new Telegram();
        when(telegramMapper.toEntity(telegramDto)).thenReturn(telegram);
        doThrow(UserAlreadyExistsException.class).when(telegramService).create(telegram);

        // Act
        ResponseEntity<TelegramDto> response = telegramController.createTelegram(telegramDto);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
    @Test
    void testUpdateTelegram_ValidTelegram_ReturnsUpdatedTelegramDto() {
        // Arrange
        TelegramDto telegramDto = new TelegramDto();
        Telegram telegram = new Telegram();
        when(telegramMapper.toEntity(telegramDto)).thenReturn(telegram);
        when(telegramService.update(telegram)).thenReturn(telegram);
        when(telegramMapper.toDto(telegram)).thenReturn(telegramDto);

        // Act
        ResponseEntity<TelegramDto> response = telegramController.updateTelegram(telegramDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(telegramDto, response.getBody());
    }

    @Test
    void testUpdateTelegram_TelegramNotFound_ReturnsNotFoundStatus() {
        // Arrange
        TelegramDto telegramDto = new TelegramDto();
        Telegram telegram = new Telegram();
        when(telegramMapper.toEntity(telegramDto)).thenReturn(telegram);
        when(telegramService.update(telegram)).thenReturn(null);

        // Act
        ResponseEntity<TelegramDto> response = telegramController.updateTelegram(telegramDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

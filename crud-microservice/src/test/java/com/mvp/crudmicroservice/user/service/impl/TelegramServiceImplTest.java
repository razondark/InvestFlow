package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.Telegram;
import com.mvp.crudmicroservice.user.repository.TelegramRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TelegramServiceImplTest {

    @Mock
    private TelegramRepository telegramRepository;

    @InjectMocks
    private TelegramServiceImpl telegramService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetById_ExistingId_ReturnsTelegram() {

        Long id = 1L;
        Telegram expectedTelegram = new Telegram();
        expectedTelegram.setId(id);
        Mockito.when(telegramRepository.findById(String.valueOf(id))).thenReturn(Optional.of(expectedTelegram));


        Telegram actualTelegram = telegramService.getById(id);


        Assertions.assertEquals(expectedTelegram, actualTelegram);
    }

    @Test
    void testGetById_NonExistingId_ThrowsResourceNotFoundException() {

        Long id = 1L;
        Mockito.when(telegramRepository.findById(String.valueOf(id))).thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class, () -> telegramService.getById(id));
    }

    @Test
    void testCreate_NewTelegram_ReturnsCreatedTelegram() {

        Telegram telegram = new Telegram();
        telegram.setTelegramId("test_telegram_id");
        Mockito.when(telegramRepository.findByTelegramId(telegram.getTelegramId())).thenReturn(Optional.empty());
        Mockito.when(telegramRepository.save(telegram)).thenReturn(telegram);


        Telegram createdTelegram = telegramService.create(telegram);


        Assertions.assertEquals(telegram, createdTelegram);
    }

    @Test
    void testCreate_ExistingTelegram_ThrowsUserAlreadyExistsException() {
        // Arrange
        Telegram telegram = new Telegram();
        telegram.setTelegramId("test_telegram_id");
        Mockito.when(telegramRepository.findByTelegramId(telegram.getTelegramId())).thenReturn(Optional.of(telegram));


        Assertions.assertThrows(UserAlreadyExistsException.class, () -> telegramService.create(telegram));
    }

    @Test
    void testDelete_ExistingTelegram_DeletesTelegram() {

        String telegramId = "test_telegram_id";


        telegramService.delete(telegramId);


        Mockito.verify(telegramRepository, Mockito.times(1)).deleteById(telegramId);
    }
    @Test
    void testGetByTelegramId_ExistingId_ReturnsTelegram() {

        String telegramId = "test_telegram_id";
        Telegram expectedTelegram = new Telegram();
        expectedTelegram.setTelegramId(telegramId);
        Mockito.when(telegramRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(expectedTelegram));


        Telegram actualTelegram = telegramService.getByTelegramId(telegramId);


        Assertions.assertEquals(expectedTelegram, actualTelegram);
    }

    @Test
    void testGetByTelegramId_NonExistingId_ThrowsResourceNotFoundException() {

        String telegramId = "non_existing_telegram_id";
        Mockito.when(telegramRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty());


        Assertions.assertThrows(ResourceNotFoundException.class, () -> telegramService.getByTelegramId(telegramId));
    }

    @Test
    void testUpdate_Telegram_ReturnsUpdatedTelegram() {

        Telegram telegram = new Telegram();
        telegram.setTelegramId("test_telegram_id");
        Mockito.when(telegramRepository.save(telegram)).thenReturn(telegram);


        Telegram updatedTelegram = telegramService.update(telegram);


        Assertions.assertEquals(telegram, updatedTelegram);
    }
}

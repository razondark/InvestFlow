package com.mvp.crudmicroservice.user.web.controller;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.Telegram;
import com.mvp.crudmicroservice.user.service.TelegramService;
import com.mvp.crudmicroservice.user.web.dto.user.TelegramDto;
import com.mvp.crudmicroservice.user.web.mapper.TelegramMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crud/telegram")
@RequiredArgsConstructor
@Slf4j
public class TelegramController {
    private final TelegramService telegramService;

    private final TelegramMapper telegramMapper;

    @PostMapping
    public ResponseEntity<TelegramDto> createTelegram(@RequestBody TelegramDto telegramDto) {
        try {
            Telegram telegram = telegramMapper.toEntity(telegramDto);
            Telegram createdTelegram = telegramService.create(telegram);

            return ResponseEntity.ok().body(telegramMapper.toDto(createdTelegram));
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<TelegramDto> updateTelegram(@RequestBody TelegramDto telegramDto) {
        Telegram telegram = telegramMapper.toEntity(telegramDto);
        Telegram updatedTelegram = telegramService.update(telegram);
        if (updatedTelegram != null) {
            return ResponseEntity.ok(telegramMapper.toDto(updatedTelegram));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity getUserById(@PathVariable Long telegramId) {
        try {
            Telegram telegram = telegramService.getById(telegramId);
            return ResponseEntity.ok().body(telegramMapper.toDto(telegram));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("error-message", e.getMessage())
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<TelegramDto> getTelegramByTelegramId(@RequestParam(value = "telegram-id") String telegramId) {
        try {
            Telegram telegram = telegramService.getByTelegramId(telegramId);
            return ResponseEntity.ok().body(telegramMapper.toDto(telegram));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{telegramId}")
    public ResponseEntity<String> deleteTelegram(@PathVariable String telegramId) {
        telegramService.delete(telegramId);
        return ResponseEntity.ok().body("Телеграм успешно удалён");
    }
}

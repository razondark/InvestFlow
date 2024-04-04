package com.mvp.crudmicroservice.user.service.impl;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.exception.UserAlreadyExistsException;
import com.mvp.crudmicroservice.user.domain.user.Telegram;
import com.mvp.crudmicroservice.user.domain.user.User;
import com.mvp.crudmicroservice.user.repository.TelegramRepository;
import com.mvp.crudmicroservice.user.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final TelegramRepository telegramRepository;
    @Override
    public Telegram getById(Long id) {
        return telegramRepository.findById(id.toString())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("There is no telegram with id %d", id)));
    }

    @Override
    public Telegram getByTelegramId(String telegramId) {
        return telegramRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Пользователя с telegram id %s не существует", telegramId)));
    }

    @Override
    public Telegram update(Telegram telegram) {
        telegramRepository.save(telegram);
        return telegram;
    }

    @Override
    public Telegram create(Telegram telegram) {
        Optional<Telegram> existTelegram = telegramRepository.findByTelegramId(telegram.getTelegramId());
        if (existTelegram.isEmpty()) {
            return telegramRepository.save(telegram);
        } else {
            throw new UserAlreadyExistsException("Пользователь с данным telegram id уже существует");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void delete(String telegramId) {
        telegramRepository.deleteById(telegramId);
    }
}

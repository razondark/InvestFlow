package com.mvp.crudmicroservice.user.service;

import com.mvp.crudmicroservice.user.domain.user.Telegram;

public interface TelegramService {
    Telegram getById(Long id);

    Telegram getByTelegramId(String telegramId);

    Telegram update(Telegram telegram);

    Telegram create(Telegram telegram);

    void delete(String telegramId);
}

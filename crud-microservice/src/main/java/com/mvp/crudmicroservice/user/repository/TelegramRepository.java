package com.mvp.crudmicroservice.user.repository;

import com.mvp.crudmicroservice.user.domain.user.Telegram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramRepository extends JpaRepository<Telegram, String> {
    Optional<Telegram> findByTelegramId(String telegramId);
}

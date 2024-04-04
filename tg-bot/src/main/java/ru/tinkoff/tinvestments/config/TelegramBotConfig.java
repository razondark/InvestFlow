package ru.tinkoff.tinvestments.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {
    @Value("${bot.name}")
    @Getter
    private String botName;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    @Value("${command.start.picture}")
    @Getter
    private String startPicture;
}

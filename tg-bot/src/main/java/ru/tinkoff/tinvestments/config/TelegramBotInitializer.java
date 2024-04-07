package ru.tinkoff.tinvestments.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.tinvestments.service.TelegramBotService;

@Component
public class TelegramBotInitializer {
    @Autowired
    TelegramBotService bot;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        try {
            var tgApi = new TelegramBotsApi(DefaultBotSession.class);
            tgApi.registerBot(bot);
        }
        catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}

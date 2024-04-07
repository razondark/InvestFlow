package ru.tinkoff.tinvestments.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.tinvestments.config.TelegramBotConfig;
import ru.tinkoff.tinvestments.model.TelegramBotCommand;

@Component
public class TelegramBotService extends TelegramLongPollingBot {
    final TelegramBotConfig botConfig;

    public TelegramBotService(TelegramBotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();

            if (message.startsWith("/")) {
                handleCommand(chatId, message);
            }
        }
    }

    private void handleCommand(Long chatId, String commandString) {
        var command = TelegramBotCommand.fromString(commandString);
        if (command == null) {
            return;
        }

        try {
            switch (command) {
                case START -> sendPicture(chatId, botConfig.getStartPicture(), "Hello world");

                default -> sendMessage(chatId, "Error");
            }
        }
        catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String text) throws TelegramApiException {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        execute(message);
    }

    private void sendPicture(Long chatId, String pictureUrl) throws TelegramApiException {
        this.sendPicture(chatId, pictureUrl, null);
    }

    private void sendPicture(Long chatId, String pictureUrl, String caption) throws TelegramApiException {
        var picture = new SendPhoto();
        picture.setChatId(chatId);
        picture.setPhoto(new InputFile().setMedia(pictureUrl));

        if (caption != null) {
            picture.setCaption(caption);
        }

        execute(picture);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }
}
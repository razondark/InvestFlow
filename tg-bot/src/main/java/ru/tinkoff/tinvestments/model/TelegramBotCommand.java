package ru.tinkoff.tinvestments.model;

public enum TelegramBotCommand {
    START("/start")

    ;

    private final String command;
    TelegramBotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static TelegramBotCommand fromString(String text) {
        for (var command : TelegramBotCommand.values()) {
            if (command.command.equalsIgnoreCase(text)) {
                return command;
            }
        }
        return null;
    }
}

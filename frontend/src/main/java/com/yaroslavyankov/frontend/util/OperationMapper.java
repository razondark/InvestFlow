package com.yaroslavyankov.frontend.util;

public enum OperationMapper {
    OPERATION_TYPE_INPUT("OPERATION_TYPE_INPUT", "Пополнение"),
    OPERATION_TYPE_BUY("OPERATION_TYPE_BUY", "Покупка"),
    OPERATION_TYPE_SELL("OPERATION_TYPE_SELL", "Продажа");

    private final String englishName;
    private final String russianName;

    OperationMapper(String englishName, String russianName) {
        this.englishName = englishName;
        this.russianName = russianName;
    }

    public static String valueOfRussianName(String englishName) {
        for (var name : values()) {
            if (name.englishName.equalsIgnoreCase(englishName)) {
                return name.russianName;
            }
        }

        return englishName;
    }
}

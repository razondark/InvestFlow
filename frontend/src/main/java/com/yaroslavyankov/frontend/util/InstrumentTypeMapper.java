package com.yaroslavyankov.frontend.util;

public enum InstrumentTypeMapper {
    ENERGY("share", "Акции"),
    FINANCIAL("bond", "Облигации"),
    HEALTH_CARE("currency", "Валюта");

    private final String englishName;
    private final String russianName;

    InstrumentTypeMapper(String englishName, String russianName) {
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

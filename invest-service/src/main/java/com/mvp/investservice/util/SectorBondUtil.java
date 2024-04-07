package com.mvp.investservice.util;

public enum SectorBondUtil {
    FINANCIAL("financial", "Финансовый сектор"),
    ENERGY("energy", "Энергетика"),
    INDUSTRIALS("industrials", "Машиностроение и транспорт"),
    GOVERNMENT("government", "Государственные бумаги"),
    CONSUMER("consumer", "Потребительские товары и услуги"),
    OTHER("other", "Другое"),
    UTILITIES("utilities", "Электроэнергетика"),
    MATERIALS("materials", "Сырьевая промышленность"),
    REAL_ESTATE("real_estate", "Недвижимость"),
    HEALTH_CARE("health_care", "Здравоохранение"),
    MUNICIPAL("municipal", "Регионы"),
    IT("it", "Информационные технологии"),
    TELECOM("telecom", "Телекоммуникации"),
    UNKNOWN("", "");

    private final String englishName;
    private final String russianName;

    SectorBondUtil(String englishName, String russianName) {
        this.englishName = englishName;
        this.russianName = russianName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getRussianName() {
        return russianName;
    }

    public static String valueOfEnglishName(String russianName) {
        for (var name : values()) {
            if (name.russianName.equalsIgnoreCase(russianName)) {
                return name.englishName;
            }
        }

        return russianName;
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

package com.yaroslavyankov.frontend.util;

public enum SectorMapper {
    ENERGY("energy", "Энергетика"),
    FINANCIAL("financial", "Финансовый сектор"),
    HEALTH_CARE("health_care", "Здравоохранение"),
    IT("it", "Информационные технологии"),
    CONSUMER("consumer", "Потребительские товары и услуги"),
    MATERIALS("materials", "Сырьевая промышленность"),
    INDUSTRIALS("industrials", "Машиностроение и транспорт"),
    TELECOM("telecom", "Телекоммуникации"),
    ECOMATERIALS("ecomaterials", "Материалы для эко-технологий"),
    OTHER("other", "Другое"),
    REAL_ESTATE("real_estate", "Недвижимость"),
    ELECTROCARS("electrocars", "Электротранспорт и комплектующие"),
    UTILITIES("utilities", "Электроэнергетика"),
    GREEN_ENERGY("green_energy", "Зеленая энергетика"),
    GREEN_BUILDINGS("green_buildings", "Энергоэффективные здания"),
    GOVERNMENT("government", "Государственные бумаги"),
    MUNICIPAL("municipal", "Регионы"),
    UNKNOWN("", "");

    private final String englishName;
    private final String russianName;

    SectorMapper(String englishName, String russianName) {
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

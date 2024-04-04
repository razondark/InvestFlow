package com.yaroslavyankov.authmicroservice.service.props;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtPropertiesTest {

    @Test
    void testGetSecret() {
        // Подготовка данных для теста
        String secret = "testSecret";
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(secret);

        // Проверка метода getSecret()
        assertEquals(secret, jwtProperties.getSecret());
    }

    @Test
    void testGetAccess() {
        // Подготовка данных для теста
        Long access = 3600L;
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setAccess(access);

        // Проверка метода getAccess()
        assertEquals(access, jwtProperties.getAccess());
    }

    @Test
    void testGetRefresh() {
        // Подготовка данных для теста
        Long refresh = 7200L;
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setRefresh(refresh);

        // Проверка метода getRefresh()
        assertEquals(refresh, jwtProperties.getRefresh());
    }

    @Test
    void testSetSecret() {
        // Подготовка данных для теста
        String secret = "testSecret";
        JwtProperties jwtProperties = new JwtProperties();

        // Установка значения с помощью метода setSecret()
        jwtProperties.setSecret(secret);

        // Проверка, что значение установлено корректно
        assertEquals(secret, jwtProperties.getSecret());
    }

    @Test
    void testSetAccess() {
        // Подготовка данных для теста
        Long access = 3600L;
        JwtProperties jwtProperties = new JwtProperties();

        // Установка значения с помощью метода setAccess()
        jwtProperties.setAccess(access);

        // Проверка, что значение установлено корректно
        assertEquals(access, jwtProperties.getAccess());
    }

    @Test
    void testSetRefresh() {
        // Подготовка данных для теста
        Long refresh = 7200L;
        JwtProperties jwtProperties = new JwtProperties();

        // Установка значения с помощью метода setRefresh()
        jwtProperties.setRefresh(refresh);

        // Проверка, что значение установлено корректно
        assertEquals(refresh, jwtProperties.getRefresh());
    }

    @Test
    void testEquals() {
        // Подготовка данных для теста
        JwtProperties jwtProperties1 = new JwtProperties();
        jwtProperties1.setSecret("secret");

        JwtProperties jwtProperties2 = new JwtProperties();
        jwtProperties2.setSecret("secret");

        // Проверка метода equals()
        assertTrue(jwtProperties1.equals(jwtProperties2));
    }

    @Test
    void testHashCode() {
        // Подготовка данных для теста
        JwtProperties jwtProperties1 = new JwtProperties();
        jwtProperties1.setSecret("secret");

        JwtProperties jwtProperties2 = new JwtProperties();
        jwtProperties2.setSecret("secret");

        // Проверка метода hashCode()
        assertEquals(jwtProperties1.hashCode(), jwtProperties2.hashCode());
    }

    @Test
    void testToString() {
        // Подготовка данных для теста
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret("testSecret");
        jwtProperties.setAccess(3600L);
        jwtProperties.setRefresh(7200L);

        // Проверка метода toString()
        assertEquals("JwtProperties(secret=testSecret, access=3600, refresh=7200)", jwtProperties.toString());
    }
}

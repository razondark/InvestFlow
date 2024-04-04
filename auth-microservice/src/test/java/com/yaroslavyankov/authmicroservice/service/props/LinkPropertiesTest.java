package com.yaroslavyankov.authmicroservice.service.props;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkPropertiesTest {

    @Test
    void testGetUserServiceLink() {
        // Подготовка данных для теста
        String userServiceLink = "http://example.com";
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setUserServiceLink(userServiceLink);

        // Проверка метода getUserServiceLink()
        assertEquals(userServiceLink, linkProperties.getUserServiceLink());
    }

    @Test
    void testSetUserServiceLink() {
        // Подготовка данных для теста
        String userServiceLink = "http://example.com";
        LinkProperties linkProperties = new LinkProperties();

        // Установка значения с помощью метода setUserServiceLink()
        linkProperties.setUserServiceLink(userServiceLink);

        // Проверка, что значение установлено корректно
        assertEquals(userServiceLink, linkProperties.getUserServiceLink());
    }

    @Test
    void testEquals() {
        // Подготовка данных для теста
        LinkProperties linkProperties1 = new LinkProperties();
        linkProperties1.setUserServiceLink("http://example.com");

        LinkProperties linkProperties2 = new LinkProperties();
        linkProperties2.setUserServiceLink("http://example.com");

        // Проверка метода equals()
        assertTrue(linkProperties1.equals(linkProperties2));
    }

    @Test
    void testHashCode() {
        // Подготовка данных для теста
        LinkProperties linkProperties1 = new LinkProperties();
        linkProperties1.setUserServiceLink("http://example.com");

        LinkProperties linkProperties2 = new LinkProperties();
        linkProperties2.setUserServiceLink("http://example.com");

        // Проверка метода hashCode()
        assertEquals(linkProperties1.hashCode(), linkProperties2.hashCode());
    }

    @Test
    void testToString() {
        // Подготовка данных для теста
        LinkProperties linkProperties = new LinkProperties();
        linkProperties.setUserServiceLink("http://example.com");

        // Проверка метода toString()
        assertEquals("LinkProperties(userServiceLink=http://example.com)", linkProperties.toString());
    }
}

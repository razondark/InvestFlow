package com.mvp.investservice.service.props;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvestPropertiesTest {

    @Test
    void getToken() {

        InvestProperties investProperties = new InvestProperties();
        String expectedToken = "testToken";
        investProperties.setToken(expectedToken);


        String actualToken = investProperties.getToken();


        assertEquals(expectedToken, actualToken);
    }

    @Test
    void setToken() {

        InvestProperties investProperties = new InvestProperties();
        String expectedToken = "testToken";


        investProperties.setToken(expectedToken);
        String actualToken = investProperties.getToken();


        assertEquals(expectedToken, actualToken);
    }

    @Test
    void testEquals() {

        InvestProperties properties1 = new InvestProperties();
        properties1.setToken("token1");

        InvestProperties properties2 = new InvestProperties();
        properties2.setToken("token1");

        InvestProperties properties3 = new InvestProperties();
        properties3.setToken("token2");


        assertEquals(properties1, properties2);
        assertNotEquals(properties1, properties3);
    }

    @Test
    void testHashCode() {

        InvestProperties properties1 = new InvestProperties();
        properties1.setToken("token1");

        InvestProperties properties2 = new InvestProperties();
        properties2.setToken("token1");


        assertEquals(properties1.hashCode(), properties2.hashCode());
    }

    @Test
    void testToString() {

        InvestProperties properties = new InvestProperties();
        properties.setToken("testToken");


        String expectedToString = "InvestProperties(token=testToken)";
        String actualToString = properties.toString();


        assertEquals(expectedToString, actualToString);
    }
}

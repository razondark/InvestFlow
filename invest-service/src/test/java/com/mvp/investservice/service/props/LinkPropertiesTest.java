package com.mvp.investservice.service.props;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkPropertiesTest {

    @Test
    void getUserServiceLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/user";
        linkProperties.setUserServiceLink(expectedLink);


        String actualLink = linkProperties.getUserServiceLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void getCreateAccountLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/create-account";
        linkProperties.setCreateAccountLink(expectedLink);


        String actualLink = linkProperties.getCreateAccountLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void getAccountServiceLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/account";
        linkProperties.setAccountServiceLink(expectedLink);


        String actualLink = linkProperties.getAccountServiceLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void setUserServiceLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/user";


        linkProperties.setUserServiceLink(expectedLink);
        String actualLink = linkProperties.getUserServiceLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void setCreateAccountLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/create-account";


        linkProperties.setCreateAccountLink(expectedLink);
        String actualLink = linkProperties.getCreateAccountLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void setAccountServiceLink() {

        LinkProperties linkProperties = new LinkProperties();
        String expectedLink = "http://example.com/account";


        linkProperties.setAccountServiceLink(expectedLink);
        String actualLink = linkProperties.getAccountServiceLink();


        assertEquals(expectedLink, actualLink);
    }

    @Test
    void testEquals() {

        LinkProperties properties1 = new LinkProperties();
        properties1.setUserServiceLink("http://example.com/user");

        LinkProperties properties2 = new LinkProperties();
        properties2.setUserServiceLink("http://example.com/user");

        LinkProperties properties3 = new LinkProperties();
        properties3.setUserServiceLink("http://example.com/other");


        assertEquals(properties1, properties2);
        assertNotEquals(properties1, properties3);
    }

    @Test
    void testHashCode() {

        LinkProperties properties1 = new LinkProperties();
        properties1.setUserServiceLink("http://example.com/user");

        LinkProperties properties2 = new LinkProperties();
        properties2.setUserServiceLink("http://example.com/user");


        assertEquals(properties1.hashCode(), properties2.hashCode());
    }

    @Test
    void testToString() {

        LinkProperties properties = new LinkProperties();
        properties.setUserServiceLink("http://example.com/user");


        String expectedToString = "LinkProperties(userServiceLink=http://example.com/user, createAccountLink=null, accountServiceLink=null)";
        String actualToString = properties.toString();


        assertEquals(expectedToString, actualToString);
    }
}

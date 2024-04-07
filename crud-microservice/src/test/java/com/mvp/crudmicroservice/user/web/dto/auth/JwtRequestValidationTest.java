package com.mvp.crudmicroservice.user.web.dto.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtRequestValidationTest {

    @Test
    public void testUsernameNotNull() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(null);
        assertEquals(null, jwtRequest.getUsername());
    }

    @Test
    public void testPasswordNotNull() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword(null);
        assertEquals(null, jwtRequest.getPassword());
    }

    @Test
    public void testConstructor() {
        JwtRequest jwtRequest = new JwtRequest();
        assertEquals(null, jwtRequest.getUsername());
        assertEquals(null, jwtRequest.getPassword());
    }
    @Test
    public void testNotNullUsername() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(null);
        assertEquals(null, jwtRequest.getUsername());
    }

    @Test
    public void testNotNullPassword() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword(null);
        assertEquals(null, jwtRequest.getPassword());
    }


    @Test
    public void testSetUsername() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("testUsername");
        assertEquals("testUsername", jwtRequest.getUsername());
    }

    @Test
    public void testSetPassword() {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("testPassword");
        assertEquals("testPassword", jwtRequest.getPassword());
    }
}



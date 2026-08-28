package com.civicconnect.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        jwtTokenProvider.jwtSecret = "mySecretKeyForCivicConnectApplicationJWTTokenGeneration";
        jwtTokenProvider.jwtExpirationMs = 86400000;
    }

    @Test
    public void testGenerateToken() {
        String email = "test@test.com";
        String token = jwtTokenProvider.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testValidateToken() {
        String email = "test@test.com";
        String token = jwtTokenProvider.generateToken(email);

        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    public void testGetEmailFromToken() {
        String email = "test@test.com";
        String token = jwtTokenProvider.generateToken(email);

        String extractedEmail = jwtTokenProvider.getEmailFromToken(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    public void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }
}

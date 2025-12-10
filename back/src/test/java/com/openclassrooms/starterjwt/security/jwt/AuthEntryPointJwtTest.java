package com.openclassrooms.starterjwt.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthEntryPointJwtTest {
    @Test
    void commence_SetsUnauthorizedStatus() throws IOException {
        AuthEntryPointJwt entryPoint = new AuthEntryPointJwt();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Unauthorized") {};
        try {
            entryPoint.commence(request, response, authException);
        } catch (jakarta.servlet.ServletException e) {
            // ignore for test
        }
        assertEquals(401, response.getStatus());
    }
}

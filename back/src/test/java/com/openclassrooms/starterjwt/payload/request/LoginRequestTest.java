package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import jakarta.validation.ConstraintViolation;

class LoginRequestTest {
    @Test
    void testLoginRequestProperties() {
        LoginRequest req = new LoginRequest();
        req.setEmail("test@test.com");
        req.setPassword("pass123");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("pass123", req.getPassword());
    }
    @Test
    void testIsPasswordAlphaNumeric_null() {
        LoginRequest req = new LoginRequest();
        req.setPassword(null);
        assertFalse(req.isPasswordAlphaNumeric());
    }

    @Test
    void testIsPasswordAlphaNumeric_onlyLetters() {
        LoginRequest req = new LoginRequest();
        req.setPassword("abcdef");
        assertFalse(req.isPasswordAlphaNumeric());
    }

    @Test
    void testIsPasswordAlphaNumeric_onlyDigits() {
        LoginRequest req = new LoginRequest();
        req.setPassword("123456");
        assertFalse(req.isPasswordAlphaNumeric());
    }

    @Test
    void testIsPasswordAlphaNumeric_lettersAndDigits() {
        LoginRequest req = new LoginRequest();
        req.setPassword("abc123");
        assertTrue(req.isPasswordAlphaNumeric());
    }

    @Test
    void testIsPasswordAlphaNumeric_specialChars() {
        LoginRequest req = new LoginRequest();
        req.setPassword("abc123!");
        assertTrue(req.isPasswordAlphaNumeric());
    }

    @Test
    void testLoginRequestValidationConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        LoginRequest req = new LoginRequest();
        req.setEmail(""); // invalid
        req.setPassword(""); // invalid
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }
        @Test
        void testIsPasswordAlphaNumeric_emptyString() {
            LoginRequest req = new LoginRequest();
            req.setPassword("");
            assertFalse(req.isPasswordAlphaNumeric());
        }

        @Test
        void testIsPasswordAlphaNumeric_whitespaceOnly() {
            LoginRequest req = new LoginRequest();
            req.setPassword("   ");
            assertFalse(req.isPasswordAlphaNumeric());
        }

        @Test
        void testIsPasswordAlphaNumeric_unicodeChars() {
            LoginRequest req = new LoginRequest();
            req.setPassword("密码123abc");
            assertTrue(req.isPasswordAlphaNumeric());
        }

        @Test
        void testValidationWhitespaceEmailPassword() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            LoginRequest req = new LoginRequest();
            req.setEmail("   ");
            req.setPassword("   ");
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertFalse(violations.isEmpty());
        }

        @Test
        void testValidationVeryLongEmailPassword() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            LoginRequest req = new LoginRequest();
            String longEmail = "a".repeat(500) + "@test.com";
            String longPassword = "p".repeat(500);
            req.setEmail(longEmail);
            req.setPassword(longPassword);
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty()); // Only NotBlank constraint
        }

        @Test
        void testEqualsSelf() {
            LoginRequest req = new LoginRequest();
            req.setEmail("self@test.com");
            req.setPassword("selfpass");
            assertEquals(req, req);
        }

        @Test
        void testEqualsMutationAfterEquals() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail("a@b.com");
            req1.setPassword("pass");
            LoginRequest req2 = new LoginRequest();
            req2.setEmail("a@b.com");
            req2.setPassword("pass");
            assertEquals(req1, req2);
            req2.setPassword("mutated");
            assertNotEquals(req1, req2);
        }

    @Test
    void testLoginRequestValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        LoginRequest req = new LoginRequest();
        req.setEmail("valid@email.com");
        req.setPassword("password123");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
        }

        @Test
        void testEqualsAndHashCode() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail("a@b.com");
            req1.setPassword("pass");
            LoginRequest req2 = new LoginRequest();
            req2.setEmail("a@b.com");
            req2.setPassword("pass");
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());
            // Not equals to null
            assertNotEquals(req1, null);
            // Not equals to other type
            assertNotEquals(req1, "string");
            // Not equals to different email
            LoginRequest req3 = new LoginRequest();
            req3.setEmail("other@b.com");
            req3.setPassword("pass");
            assertNotEquals(req1, req3);
            // Mutation after construction
            req1.setEmail("mutated@b.com");
            assertNotEquals(req1, req2);
        }

        @Test
        void testLoginRequestExtremeValues() {
            LoginRequest req = new LoginRequest();
            req.setEmail("");
            req.setPassword("");
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertFalse(violations.isEmpty());
            req.setEmail("a@b.com");
            req.setPassword("p"); // too short
            violations = validator.validate(req);
            assertTrue(violations.isEmpty()); // NotBlank only, no length constraint
        }

        @Test
        void testLoginRequestNullValues() {
            LoginRequest req = new LoginRequest();
            req.setEmail(null);
            req.setPassword(null);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertFalse(violations.isEmpty());
        }

        @Test
        void testLoginRequestEqualsBranches() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail("a@b.com");
            req1.setPassword("pass");
            LoginRequest req2 = new LoginRequest();
            req2.setEmail("a@b.com");
            req2.setPassword("pass");
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());
            assertNotEquals(req1, null);
            assertNotEquals(req1, "string");
            req1.setEmail("other@b.com");
            assertNotEquals(req1, req2);
        }

    @Test
    void testValidationAndEdgeCases() {
        LoginRequest req = new LoginRequest();
        // Test blank email/password
        req.setEmail("");
        req.setPassword("");
        assertEquals("", req.getEmail());
        assertEquals("", req.getPassword());
        // Test normal values
        req.setEmail("a@b.com");
        req.setPassword("123456");
        assertEquals("a@b.com", req.getEmail());
        assertEquals("123456", req.getPassword());
    }

    @Test
    void testToStringSpecialValues() {
        LoginRequest req = new LoginRequest();
        req.setEmail(null);
        req.setPassword(null);
        assertNotNull(req.toString());
        req.setEmail("\n\t\u2603@test.com");
        assertTrue(req.toString().contains("\u2603"));
    }

        @Test
        void testEqualsDifferentPassword() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail("a@b.com");
            req1.setPassword("pass1");
            LoginRequest req2 = new LoginRequest();
            req2.setEmail("a@b.com");
            req2.setPassword("pass2");
            assertNotEquals(req1, req2);
        }

        @Test
        void testEqualsNullFields() {
            LoginRequest req1 = new LoginRequest();
            req1.setEmail(null);
            req1.setPassword(null);
            LoginRequest req2 = new LoginRequest();
            req2.setEmail(null);
            req2.setPassword(null);
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());
        }

        @Test
        void testSettersAndGettersBranches() {
            LoginRequest req = new LoginRequest();
            req.setEmail(null);
            req.setPassword(null);
            assertNull(req.getEmail());
            assertNull(req.getPassword());
            req.setEmail("test@domain.com");
            req.setPassword("azerty123");
            assertEquals("test@domain.com", req.getEmail());
            assertEquals("azerty123", req.getPassword());
        }

        @Test
        void testValidationEmailWithSpaces() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            LoginRequest req = new LoginRequest();
            req.setEmail("  test@domain.com  ");
            req.setPassword("password");
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testValidationPasswordWithSpaces() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            LoginRequest req = new LoginRequest();
            req.setEmail("test@domain.com");
            req.setPassword("  password  ");
            Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
            assertTrue(violations.isEmpty());
        }
}

package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import jakarta.validation.ConstraintViolation;

class SignupRequestTest {
    @Test
    void testSignupRequestProperties() {
        SignupRequest req = new SignupRequest();
        req.setEmail("test@test.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("pass123");
        assertEquals("test@test.com", req.getEmail());
        assertEquals("John", req.getFirstName());
        assertEquals("Doe", req.getLastName());
        assertEquals("pass123", req.getPassword());
    }
    @Test
    void testIsStrongPassword_null() {
        SignupRequest req = new SignupRequest();
        req.setPassword(null);
        assertFalse(req.isStrongPassword());
    }

    @Test
    void testIsStrongPassword_shortNoDigit() {
        SignupRequest req = new SignupRequest();
        req.setPassword("abcdefg"); // 7 chars, no digit
        assertFalse(req.isStrongPassword());
    }

    @Test
    void testIsStrongPassword_longNoDigit() {
        SignupRequest req = new SignupRequest();
        req.setPassword("abcdefgh"); // 8 chars, no digit
        assertFalse(req.isStrongPassword());
    }

    @Test
    void testIsStrongPassword_shortWithDigit() {
        SignupRequest req = new SignupRequest();
        req.setPassword("abc123"); // 6 chars, has digit
        assertFalse(req.isStrongPassword());
    }

    @Test
    void testIsStrongPassword_longWithDigit() {
        SignupRequest req = new SignupRequest();
        req.setPassword("abcd1234"); // 8 chars, has digit
        assertTrue(req.isStrongPassword());
    }

    @Test
    void testSignupRequestValidationConstraints() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SignupRequest req = new SignupRequest();
        req.setEmail(""); // invalid
        req.setFirstName("A"); // too short
        req.setLastName(""); // invalid
        req.setPassword("123"); // too short
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testSignupRequestValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        SignupRequest req = new SignupRequest();
        req.setEmail("valid@email.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("password123");
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty());
        }

        @Test
        void testEqualsAndHashCode() {
            SignupRequest req1 = new SignupRequest();
            req1.setEmail("a@b.com");
            req1.setFirstName("John");
            req1.setLastName("Doe");
            req1.setPassword("pass");
            SignupRequest req2 = new SignupRequest();
            req2.setEmail("a@b.com");
            req2.setFirstName("John");
            req2.setLastName("Doe");
            req2.setPassword("pass");
            assertEquals(req1, req2);
            assertEquals(req1.hashCode(), req2.hashCode());
            // Not equals to null
            assertNotEquals(req1, null);
            // Not equals to other type
            assertNotEquals(req1, "string");
            // Not equals to different email
            SignupRequest req3 = new SignupRequest();
            req3.setEmail("other@b.com");
            req3.setFirstName("John");
            req3.setLastName("Doe");
            req3.setPassword("pass");
            assertNotEquals(req1, req3);
            // Mutation after construction
            req1.setEmail("mutated@b.com");
            assertNotEquals(req1, req2);
            // Extreme values
            SignupRequest reqExtreme = new SignupRequest();
            reqExtreme.setEmail("");
            reqExtreme.setFirstName("");
            reqExtreme.setLastName("");
            reqExtreme.setPassword("");
            assertEquals("", reqExtreme.getEmail());
            assertEquals("", reqExtreme.getFirstName());
            assertEquals("", reqExtreme.getLastName());
            assertEquals("", reqExtreme.getPassword());
    }

    @Test
    void testSignupRequestExtremeValues() {
        SignupRequest req = new SignupRequest();
        req.setEmail("");
        req.setFirstName("A");
        req.setLastName("");
        req.setPassword("123");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        req.setEmail("a@b.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPassword("p"); // too short
        violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testSignupRequestNullValues() {
        SignupRequest req = new SignupRequest();
        req.setEmail(null);
        req.setFirstName(null);
        req.setLastName(null);
        req.setPassword(null);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testSignupRequestEqualsBranches() {
        SignupRequest req1 = new SignupRequest();
        req1.setEmail("a@b.com");
        req1.setFirstName("John");
        req1.setLastName("Doe");
        req1.setPassword("pass");
        SignupRequest req2 = new SignupRequest();
        req2.setEmail("a@b.com");
        req2.setFirstName("John");
        req2.setLastName("Doe");
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
        SignupRequest req = new SignupRequest();
        // Test blank email
        req.setEmail("");
        assertEquals("", req.getEmail());
        // Test max size email
        String longEmail = "a".repeat(40) + "@test.com";
        req.setEmail(longEmail);
        assertEquals(longEmail, req.getEmail());
        // Test min/max size firstName/lastName
        req.setFirstName("abc");
        req.setLastName("abc");
        assertEquals("abc", req.getFirstName());
        assertEquals("abc", req.getLastName());
        String longName = "b".repeat(20);
        req.setFirstName(longName);
        req.setLastName(longName);
        assertEquals(longName, req.getFirstName());
        assertEquals(longName, req.getLastName());
        // Test min/max size password
        req.setPassword("123456");
        assertEquals("123456", req.getPassword());
        String longPass = "c".repeat(40);
        req.setPassword(longPass);
        assertEquals(longPass, req.getPassword());
    }

    @Test
    void testToStringSpecialValues() {
        SignupRequest req = new SignupRequest();
        req.setEmail(null);
        req.setFirstName(null);
        req.setLastName(null);
        req.setPassword(null);
        assertNotNull(req.toString());
        req.setEmail("\n\t\u2603@test.com");
        assertTrue(req.toString().contains("\u2603"));
    }
        @Test
        void testMutationToNullAfterInit() {
            SignupRequest req = new SignupRequest();
            req.setEmail("a@b.com");
            req.setFirstName("John");
            req.setLastName("Doe");
            req.setPassword("pass");
            // Mutate all to null
            req.setEmail(null);
            req.setFirstName(null);
            req.setLastName(null);
            req.setPassword(null);
            assertNull(req.getEmail());
            assertNull(req.getFirstName());
            assertNull(req.getLastName());
            assertNull(req.getPassword());
            // equals/hashCode with all nulls
            SignupRequest allNull = new SignupRequest();
            allNull.setEmail(null);
            allNull.setFirstName(null);
            allNull.setLastName(null);
            allNull.setPassword(null);
            assertEquals(req, allNull);
            assertEquals(req.hashCode(), allNull.hashCode());
            // toString should not throw
            assertNotNull(req.toString());
        }

        @Test
        void testEqualsHashCodeCombinations() {
            SignupRequest base = new SignupRequest();
            base.setEmail("a@b.com");
            base.setFirstName("John");
            base.setLastName("Doe");
            base.setPassword("pass");
            // All nulls
            SignupRequest allNull = new SignupRequest();
            allNull.setEmail(null);
            allNull.setFirstName(null);
            allNull.setLastName(null);
            allNull.setPassword(null);
            assertNotEquals(base, allNull);
            assertNotEquals(base.hashCode(), allNull.hashCode());
            // Only email null
            SignupRequest emailNull = new SignupRequest();
            emailNull.setEmail(null);
            emailNull.setFirstName("John");
            emailNull.setLastName("Doe");
            emailNull.setPassword("pass");
            assertNotEquals(base, emailNull);
            // Only firstName null
            SignupRequest firstNameNull = new SignupRequest();
            firstNameNull.setEmail("a@b.com");
            firstNameNull.setFirstName(null);
            firstNameNull.setLastName("Doe");
            firstNameNull.setPassword("pass");
            assertNotEquals(base, firstNameNull);
            // Only lastName null
            SignupRequest lastNameNull = new SignupRequest();
            lastNameNull.setEmail("a@b.com");
            lastNameNull.setFirstName("John");
            lastNameNull.setLastName(null);
            lastNameNull.setPassword("pass");
            assertNotEquals(base, lastNameNull);
            // Only password null
            SignupRequest passwordNull = new SignupRequest();
            passwordNull.setEmail("a@b.com");
            passwordNull.setFirstName("John");
            passwordNull.setLastName("Doe");
            passwordNull.setPassword(null);
            assertNotEquals(base, passwordNull);
        }
}

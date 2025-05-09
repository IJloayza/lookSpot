package com.example.lookspot

import com.example.lookspot.extras.testing.LogInValidator
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test

class LoginActivityTest {
    @Test
    fun `isValidEmail should return true for valid email`() {
        try {
            LogInValidator.validateEmail("correctEmail@test.com")
            assertEquals(1, 1)
        }catch (e: IllegalArgumentException){
           fail("Correct email should not throw an exception, ${e.message}")
        }
    }

    @Test
    fun `Email empty should return error`(){
        try {
            LogInValidator.validateEmail("")
            fail("A IllegalArgumentException was expected")
        } catch (e: IllegalArgumentException) {
            assertEquals("Email must not be empty", e.message)
        }
    }

    @Test
    fun `Email with incorrect format should return error`(){
        val invalidEmails = mapOf(
            "@test.cat" to "Invalid email format",
            "test@.com" to "Invalid email format",
            "test@com" to "Invalid email format"
        )

        for ((email, errorMessage) in invalidEmails) {
            try {
                LogInValidator.validateEmail(email)
                fail("A IllegalArgumentException was expected")
            } catch (e: IllegalArgumentException) {
                assertEquals(errorMessage, e.message)
            }
        }
    }

    @Test
    fun `Email cannot be longer than 50 characters`() {
        try {
            LogInValidator.validateEmail("thisisatestemailfor50characterslong@reallylongemail.com")
            fail("A IllegalArgumentException was expected")
        } catch (e: IllegalArgumentException) {
            assertEquals("Email must not exceed 50 characters", e.message)
        }
    }

    @Test
    fun `Password cannot be empty`() {
        try {
            LogInValidator.validatePassword("")
            fail("A IllegalArgumentException was expected")
        } catch (e: IllegalArgumentException) {
            assertEquals("Password must not be empty", e.message)
        }
    }
}
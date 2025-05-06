package com.example.lookspot

import com.example.lookspot.extras.testing.LogInValidator
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test

class MainActivityTest {
    @Test
    fun `isValidEmail should return true for valid email`() {

    }

    @Test(expected = IllegalArgumentException::class)
    fun `Email empty should return error`(){
        try {
            LogInValidator.validateEmail("")
            fail("A IllegalArgumentException was expected")
        } catch (e: IllegalArgumentException) {
            assertEquals("The email must not be empty", e.message)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Email with incorrect format should return error`(){
        try {
            val invalidEmails = mapOf(
                "" to "The email must not be empty",
                "@test.cat" to "Invalid email format",
                "test@.com" to "Invalid email format",
                "test@com" to "Invalid email format"
            )

            for ((email, errorMessage) in invalidEmails) {
                LogInValidator.validateEmail("@test.cat")
                fail("A IllegalArgumentException was expected")
            }
        } catch (e: IllegalArgumentException) {
            assertEquals("The email must not be empty", e.message)
        }
    }



    @Test
    fun `isValidEmail should return false for invalid email`() {

    }

}
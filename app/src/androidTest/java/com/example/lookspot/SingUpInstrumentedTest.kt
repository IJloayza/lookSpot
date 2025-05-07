package com.example.lookspot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lookspot.screens.SignUpActivity
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SingUpInstrumentedTest {

    interface EmailValidationTest{}
    interface PasswordValidationTest{}
    interface UserNameValidationTest{}

    @get:Rule
    var activityRule = ActivityScenarioRule(SignUpActivity::class.java)


    @Category(EmailValidationTest::class)
    @Test
    fun test_missing_email_field() {

        onView(withId(R.id.emailAddress)).perform(clearText())
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.emailAddress))
            .check(matches(hasErrorText("Email must not be empty")))
    }

    @Category(EmailValidationTest::class)
    @Test
    fun test_inavlid_email_format() {

        val invalidEmails = listOf(
            "@example.com",
            "example.com",
            "example@",
            "example@.com"
        )

        invalidEmails.forEach { invalidEmail ->
            onView(withId(R.id.emailAddress)).perform(clearText(), typeText(invalidEmail), closeSoftKeyboard())
            onView(withId(R.id.credsLogin)).perform(click())

            onView(withId(R.id.emailAddress))
                .check(matches(hasErrorText("Invalid email format")))
        }
    }

    @Category(EmailValidationTest::class)
    @Test
    fun test_valid_email_length() {

        val longEmail = "a@a.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"

        onView(withId(R.id.emailAddress)).perform(typeText(longEmail))
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.emailAddress))
            .check(matches(hasErrorText("Email must not exceed 50 characters")))
    }

    @Category(PasswordValidationTest::class)
    @Test
    fun test_missing_password_field() {

        onView(withId(R.id.password)).perform(clearText())
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.password))
            .check(matches(hasErrorText("Password must not be empty")))
    }

    @Category(PasswordValidationTest::class)
    @Test
    fun test_invalid_password_length() {

        val longPassword = "aaaaaaaaaaaaaaaaaaaa"
        val shortPassword = "aaaaaaa"

        onView(withId(R.id.password)).perform(typeText(longPassword))
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.password))
            .check(matches(hasErrorText("Password must not exceed 20 characters")))

        onView(withId(R.id.password)).perform(clearText(), typeText(shortPassword))
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.password))
            .check(matches(hasErrorText("Password must be at least 8 characters long")))

    }

    @Category(PasswordValidationTest::class)
    @Test
    fun test_too_weak_password() {
        val weakPasswords = listOf(
            "password123",
            "Password123",
            "12",
            "password",
            "PASSWORD123",
        )

        weakPasswords.forEach { weakPassword ->
            onView(withId(R.id.password)).perform(clearText(), typeText(weakPassword))
            onView(withId(R.id.credsLogin)).perform(click())

            onView(withId(R.id.password))
                .check(matches(hasErrorText("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")))
        }
    }

    @Category(UserNameValidationTest::class)
    @Test
    fun test_missing_username_field() {
        onView(withId(R.id.fullName)).perform(clearText())
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.fullName))
            .check(matches(hasErrorText("Username must not be empty")))

    }

    @Category(UserNameValidationTest::class)
    @Test
    fun test_invalid_username_length() {
        val longUsername = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        val shortUsername = "aaaaaaa"

        onView(withId(R.id.fullName)).perform(typeText(longUsername))
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.fullName))
            .check(matches(hasErrorText("Username must not exceed 50 characters")))

        onView(withId(R.id.fullName)).perform(clearText(), typeText(shortUsername))
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.fullName))
            .check(matches(hasErrorText("Username must be at least 2 characters long")))
    }

    @Category(UserNameValidationTest::class)
    @Test
    fun test_invalid_username_format() {
        val invalidUsernames = listOf(
            "user1",
            "user@",
            "user!",
            "user#",
            "12345678",
            " user",
            "user ",
            "!user"
        )

        invalidUsernames.forEach { invalidUsername ->
            onView(withId(R.id.fullName)).perform(clearText(), typeText(invalidUsername))
            onView(withId(R.id.credsLogin)).perform(click())

            onView(withId(R.id.fullName))
                .check(matches(hasErrorText("Name must contain only letters and spaces")))
        }

    }




}
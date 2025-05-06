package com.example.lookspot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lookspot.screens.MainActivity

import org.junit.Rule
import org.junit.experimental.categories.Category
import org.junit.Test
import org.junit.runner.RunWith

/*
Elements clau d'Espresso en els tests
onView(withId(...)) → Localitza un element de la UI.
perform(typeText(...)) → Escriu text en un EditText.
perform(click()) → Fa clic en un Button.
check(matches(...)) → Verifica que l'element compleix una condició.
hasErrorText("Missatge d'error") → Comprova si un EditText té un error assignat.
isDisplayed() → Comprova si un element és visible a la pantalla.

 */

@RunWith(AndroidJUnit4::class)
class RegistreActivityUITest {

    interface EmailValidationTest{}
    interface PasswordValidationTest{}

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


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

        invalidEmails.forEach { invalidEmail -> // Clear and type new email
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
            .check(matches(hasErrorText("Email length must be between 5 and 50 characters")))
    }

    @Category(PasswordValidationTest::class)
    @Test
    fun test_missing_password_field() {

        onView(withId(R.id.password)).perform(clearText())
        onView(withId(R.id.credsLogin)).perform(click())

        onView(withId(R.id.password))
            .check(matches(hasErrorText("Password must not be empty")))
    }
}
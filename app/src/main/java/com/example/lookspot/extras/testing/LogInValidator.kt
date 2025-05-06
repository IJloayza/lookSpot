package com.example.lookspot.extras.testing

object LogInValidator {

    fun validateEmail(email: String): String? {
        val emailRegex = Regex("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")
        var error: String? = null

        if (email.isBlank()) {
            error = "Email must not be empty"
        }
        else if (!emailRegex.matches(email)) {
            error = "Invalid email format"
        }
        else if (email.length > 50) {
            error = "Email must not exceed 50 characters"
        }

        return error
    }

    fun validatePassword(password: String): String? {
        var error: String? = null

        if (password.isBlank()) {
            error = "Password must not be empty"
        }

        return error
    }

}
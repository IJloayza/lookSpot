package com.example.lookspot.extras.testing

object LogInValidator {

    fun validateEmail(email: String){
        val emailRegex = Regex("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")

        if (email.isBlank()) {
            throw IllegalArgumentException("Email must not be empty")
        }
        else if (!emailRegex.matches(email)) {
            throw IllegalArgumentException("Invalid email format")
        }
        else if (email.length > 50) {
            throw IllegalArgumentException("Email must not exceed 50 characters")
        }
    }

    fun validatePassword(password: String){
        if (password.isBlank()) {
            throw IllegalArgumentException("Password must not be empty")
        }
    }

}
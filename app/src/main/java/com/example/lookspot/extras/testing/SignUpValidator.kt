package com.example.lookspot.extras.testing

object SignUpValidator {

    fun validatePassword(password: String){
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")

        if (password.isBlank()) {
            throw IllegalArgumentException("Password must not be empty")
        }
        else if (password.length < 8) {
            throw IllegalArgumentException("Password must be at least 8 characters long")
        }
        else if (password.length > 20) {
            throw IllegalArgumentException("Password must not exceed 20 characters")
        }
        else if (!passwordRegex.matches(password)) {
            throw IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
        }
    }

    fun validateName(name: String){
        val nameRegex = Regex("^[a-zA-Z ]+$")

        if (name.isBlank()) {
            throw IllegalArgumentException("Name must not be empty")
        }
        else if (name.length > 50) {
            throw IllegalArgumentException("Name must not exceed 50 characters")
        }
        else if (!nameRegex.matches(name)) {
            throw IllegalArgumentException("Name must contain only letters and spaces")
        }
    }

}
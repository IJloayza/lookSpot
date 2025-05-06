package com.example.lookspot.extras.testing

object SignUpValidator {

    fun validatePassword(password: String): String? {
        var error: String? = null
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")

        if (password.isBlank()) {
            error = "Password must not be empty"
        }
        else if (password.length < 8) {
            error = "Password must be at least 8 characters long"
        }
        else if (password.length > 20) {
            error = "Password must not exceed 20 characters"
        }
        else if (!passwordRegex.matches(password)) {
            error = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        }


        return error
    }

    fun validateName(name: String): String? {
        var error: String? = null
        val nameRegex = Regex("^[a-zA-Z ]+$")

        if (name.isBlank()) {
            error = "Name must not be empty"
        }
        else if (name.length > 50) {
            error = "Name must not exceed 50 characters"
        }
        else if (!nameRegex.matches(name)) {
            error = "Name must contain only letters and spaces"
        }

        return error
    }

}
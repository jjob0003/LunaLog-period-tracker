package com.example.a5046.presentation.util



object ValidationUtils {

    fun isvalidatePassword(password: String): Boolean {
        // Check if password length is greater than 6
        val isLongEnough = password.length > 6
        // Check for at least one uppercase letter
        val containsUppercase = password.any { it.isUpperCase() }

        // Return true only if both conditions are met
        return isLongEnough && containsUppercase
    }



}
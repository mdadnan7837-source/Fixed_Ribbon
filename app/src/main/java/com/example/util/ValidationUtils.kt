package com.example.util

import android.util.Patterns

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun doPasswordsMatch(password: String, confirm: String): Boolean {
        return password == confirm
    }

    fun isValidName(name: String): Boolean {
        return name.trim().length >= 2
    }

    fun isValidAge(ageStr: String): Boolean {
        val age = ageStr.toIntOrNull()
        return age != null && age in 12..120
    }

    fun isValidHeight(heightStr: String): Boolean {
        val height = heightStr.toDoubleOrNull()
        return height != null && height in 50.0..250.0
    }

    fun isValidWeight(weightStr: String): Boolean {
        val weight = weightStr.toDoubleOrNull()
        return weight != null && weight in 20.0..300.0
    }
}

package com.maxbay.domain.booking.usecases.email

import java.util.regex.Pattern

object IsValidEmail {
    private const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

    fun isValid(email: String): Boolean {
        return Pattern.matches(EMAIL_PATTERN, email)
    }
}
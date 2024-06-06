package com.cooliewale.login.utils

object AuthValidator {
    fun isValidPhoneNumber(phoneNumber: String): Boolean = phoneNumber.trim().length == 10 && phoneNumber.all { it.isDigit() }
}
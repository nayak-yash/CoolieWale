package com.cooliewale.login.viewmodel.states

data class OTPState(
    val phoneNumber: String,
    val otp: String = "",
    val success: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
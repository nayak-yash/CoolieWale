package com.cooliewale.login.viewmodel.states

data class LoginState(
    val phoneNumber: String = "",
    val isValidPhoneNumber: Boolean? = null,
    val success: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
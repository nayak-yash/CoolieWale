package com.cooliewale.login.viewmodel

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooliewale.login.repository.AuthRepository
import com.cooliewale.login.utils.AuthValidator
import com.cooliewale.login.utils.NetworkResult
import com.cooliewale.login.viewmodel.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var loginState: LoginState by mutableStateOf(LoginState())

    private var job: Job? = null

    fun createUserWithPhone(mobile: String, activity: Activity) {
        if (!validateCredentials()) return
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            repository.createUserWithPhone(mobile, activity).collect {
                loginState = when (it) {
                    is NetworkResult.Failure -> loginState.copy(error = it.error.message, isLoading = false)
                    is NetworkResult.Loading -> loginState.copy(isLoading = true)
                    is NetworkResult.Success -> loginState.copy(success = it.data, isLoading = false)
                }
            }
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        loginState = loginState.copy(phoneNumber = phoneNumber)
    }

    private fun validateCredentials(): Boolean {
        val isValidPhoneNumber = AuthValidator.isValidPhoneNumber(loginState.phoneNumber)
        loginState = loginState.copy(isValidPhoneNumber = isValidPhoneNumber)
        return isValidPhoneNumber
    }

    fun clearError() {
        loginState = loginState.copy(error = null)
    }

    fun setSuccess(success: String?) {
        loginState = loginState.copy(success = success)

    }
}
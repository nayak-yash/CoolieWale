package com.cooliewale.login.viewmodel

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooliewale.login.repository.AuthRepository
import com.cooliewale.login.utils.NetworkResult
import com.cooliewale.login.viewmodel.states.OTPState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val phoneNumber: String = savedStateHandle["number"]!!

    var otpState: OTPState by mutableStateOf(OTPState(phoneNumber = phoneNumber))

    private var job1: Job? = null

    private var job2: Job? = null

    fun createUserWithPhone(activity: Activity) {
        job1?.cancel()
        job1 = viewModelScope.launch {
            delay(500)
            repository.createUserWithPhone(otpState.phoneNumber, activity).collect {
                otpState = when (it) {
                    is NetworkResult.Failure -> otpState.copy(error = it.error.message, isLoading = false)
                    is NetworkResult.Loading -> otpState.copy(isLoading = true)
                    is NetworkResult.Success -> otpState.copy(success = it.data, isLoading = false)
                }
            }
        }
    }

    fun signInWithCredential() {
        job2?.cancel()
        job2 = viewModelScope.launch {
            delay(500)
            repository.signWithCredential(otpState.otp).collect {
                otpState = when (it) {
                    is NetworkResult.Failure -> otpState.copy(error = it.error.message, isLoading = false)
                    is NetworkResult.Loading -> otpState.copy(isLoading = true)
                    is NetworkResult.Success -> otpState.copy(success = it.data, isLoading = false)
                }
            }
        }
    }

    fun setOtp(otp: String) {
        otpState = otpState.copy(otp = otp)
    }

    fun clearError() {
        otpState = otpState.copy(error = null)
    }

    fun setSuccess(success: String?) {
        otpState = otpState.copy(success = success)
    }
}
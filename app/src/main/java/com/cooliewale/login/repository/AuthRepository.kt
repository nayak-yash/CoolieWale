package com.cooliewale.login.repository

import android.app.Activity
import com.cooliewale.login.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createUserWithPhone(phone: String, activity: Activity) : Flow<NetworkResult<String>>

    fun signWithCredential(otp: String): Flow<NetworkResult<String>>
}
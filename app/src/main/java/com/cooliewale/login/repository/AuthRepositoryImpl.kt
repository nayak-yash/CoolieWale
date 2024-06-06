package com.cooliewale.login.repository

import android.app.Activity
import com.cooliewale.login.utils.NetworkResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDb:FirebaseAuth
) : AuthRepository {

    companion object {
        private lateinit var onVerificationCode: String
    }

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<NetworkResult<String>> =  callbackFlow{
        trySend(NetworkResult.Loading)

        val onVerificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

            override fun onVerificationFailed(p0: FirebaseException) {
                trySend(NetworkResult.Failure(p0))
            }

            override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationCode, p1)
                trySend(NetworkResult.Success("OTP Sent Successfully"))
                onVerificationCode = verificationCode
            }
        }

        val options = PhoneAuthOptions.newBuilder(authDb)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(onVerificationCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose{
            close()
        }
    }

    override fun signWithCredential(otp: String): Flow<NetworkResult<String>>  = callbackFlow{
        trySend(NetworkResult.Loading)
        val credential = PhoneAuthProvider.getCredential(onVerificationCode,otp)
        authDb.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    trySend(NetworkResult.Success("OTP Verified"))
            }.addOnFailureListener {
                trySend(NetworkResult.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}
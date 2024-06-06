package com.cooliewale.login.utils

sealed class NetworkResult<out T> {
    data class Success<out R>(val data: R): NetworkResult<R>()
    data class Failure(val error: Throwable): NetworkResult<Nothing>()
    data object Loading: NetworkResult<Nothing>()
}
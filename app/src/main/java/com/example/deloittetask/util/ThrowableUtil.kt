package com.example.deloittetask.util

import android.os.Parcelable
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class DeloitteError {
    object NoInternetConnection : DeloitteError()
    object TimeOutConnection : DeloitteError()
    data class ServerError(val errorMessage: String) : DeloitteError()
    data class GenericError(val errorMessage: String) : DeloitteError()
    data class LocalError(val errorCode: Int) : DeloitteError()
}


val Throwable.isConnectionException: Boolean
    get() = this is UnknownHostException ||
            this is NoRouteToHostException ||
            this is SocketTimeoutException ||
            this.cause?.isConnectionException ?: false

val Throwable.isConnectionTimeoutException: Boolean
    get() = this is ConnectException ||
            this is SocketTimeoutException ||
            this.cause?.isConnectionException ?: false

fun Throwable.mapToDeloitteError(errorCode: Int? = null): DeloitteError {
    return when {
        isConnectionException -> {
            DeloitteError.NoInternetConnection
        }

        isConnectionTimeoutException -> {
            DeloitteError.TimeOutConnection
        }

        this is IOException -> {
            DeloitteError.ServerError(this.message ?: "")
        }

        else -> {
            DeloitteError.GenericError(this.message ?: "")
        }
    }
}
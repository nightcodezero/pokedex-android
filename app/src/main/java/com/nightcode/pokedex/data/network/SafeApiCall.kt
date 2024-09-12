package com.nightcode.pokedex.data.network

import com.nightcode.pokedex.data.network.model.BaseResponse
import com.nightcode.pokedex.utils.Connectivity
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.SocketFactory

internal suspend fun <T : BaseResponse> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> =
    try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body is null"))
        } else {
            Result.failure(mapToException(HttpException(response)))
        }
    } catch (
        @Suppress("TooGenericExceptionCaught") e: Exception,
    ) {
        Timber.e(e)
        Result.failure(mapToException(e))
    }

internal fun mapToException(throwable: Throwable?): Throwable =
    when (throwable) {
        is HttpException -> Exception(throwable.response()?.errorBody().toString())
        is ConnectException, is UnknownHostException, is SocketTimeoutException, is IOException -> {
            if (Connectivity.checkInternetConnection(SocketFactory.getDefault()).isSuccess)
                 Exception(throwable.localizedMessage.orEmpty())
            else
                Exception("No internet connection")
        }
        else -> Exception("Unknown error")
    }
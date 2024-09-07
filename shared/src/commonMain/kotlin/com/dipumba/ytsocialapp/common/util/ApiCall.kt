package com.dipumba.ytsocialapp.common.util

import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

internal suspend fun <T> safeApiCall(
    dispatcher: DispatcherProvider,
    errorHandler: (Throwable) -> Result<T> = { defaultErrorHandler(it) },
    apiCall: suspend () -> Result<T>
): Result<T> = withContext(dispatcher.io) {
    try {
        apiCall()
    }catch (exception: Throwable){
        if (exception is CancellationException) throw exception
        errorHandler(exception)
    }
}

private fun <T> defaultErrorHandler(error: Throwable): Result<T>{
    return if (error is IOException){
        Result.Error(message = Constants.NO_INTERNET_ERROR)
    }else{
        Result.Error(message = error.message ?: Constants.UNEXPECTED_ERROR)
    }
}
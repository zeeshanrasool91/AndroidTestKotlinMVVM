package ae.android.test.networking.api

import ae.android.test.networking.response.ErrorResponse


sealed class ResultWrapper<out T> {
    class Success<T>(var responseBody: T) : ResultWrapper<T>()
    data class Failed<T>(val responseErrorBody: ErrorResponse): ResultWrapper<T>()
    class Exception<T>(val responseExceptionBody: Throwable) : ResultWrapper<T>()
}
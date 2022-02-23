package com.android.core.network

import java.io.IOException


/**
 * Created by Musthofa Ali Ubaed <panic.inc.dev@gmail.com> on 07/05/2020.
 */
typealias GenericResponse<S> = NetworkResponse<S, Error>

data class GenericError(val code: Int, val message: String)

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}
package com.kyawlinnthant.onetouch.common

sealed class DataResult<out T>(
    open val data: T? = null
) {
    data class Success<out T>(override val data: T) : DataResult<T>()
    data class Fail(val message: String) : DataResult<Nothing>()
}


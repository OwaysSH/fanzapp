package com.fanz.oways.model


sealed class DataState<out R> {
    data class Success<out T>(val data: T, val responseCode: Int?) : DataState<T>()
    data class Error(val throwable: Throwable) : DataState<Nothing>()
    data object Empty : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
}

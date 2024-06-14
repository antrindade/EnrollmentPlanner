package com.example.enrollmentplanner.core.state

sealed class DataState<out T> where T : Any? {
    data object Loading : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: ErrorType) : DataState<Nothing>()
}

interface ErrorType
package com.example.mylibrary.models

import java.lang.Exception

sealed class Response<out T : Any> {
    data class Success<out T : Any>(val el : T) : Response<T>()
    data class Error(val ex : Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
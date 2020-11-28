package com.example.loaner.api

sealed class ResultData <out R>{
    data class Success<out T>(
        val value: T?
    ): ResultData<T>()
    data class Error(
        val value: Throwable
    ): ResultData<Nothing>()
}
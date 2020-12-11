package com.example.loaner.network

import android.util.Log
import com.example.loaner.App.Companion.BASE_URL
import com.example.loaner.api.AuthService
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthorizationInterceptor @Inject constructor() : Interceptor {

    companion object {
        var authToken: String? = null
    }

    private val loginRequestUrl = "${BASE_URL}login"
    private val registerRequestUrl = "${BASE_URL}registration"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().toString()
        if (url != loginRequestUrl && url != registerRequestUrl) {
            authToken?.let {
                request = request.newBuilder()
                    .header("Authorization", it)
                    .build()
            }
        }
        return chain.proceed(request)
    }
}
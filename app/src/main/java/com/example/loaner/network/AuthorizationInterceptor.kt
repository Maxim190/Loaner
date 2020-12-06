package com.example.loaner.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthorizationInterceptor @Inject constructor(): Interceptor {
    val t = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobyIsImV4cCI6MTYwOTI1MTU0MX0.tdGSM-8ar1g5cX4cURCiyPxCTLvVB98IZwc7cCKi1OKFk0RPHY1DG8jx-t-SPNrYxbcKUTt1_OLMxE2jrewAHA"
    companion object {
        var authToken: String? = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        authToken?.let {
            request = request.newBuilder()
                .header("Authorization", it)
                .build()
        }
        return chain.proceed(request)
    }
}
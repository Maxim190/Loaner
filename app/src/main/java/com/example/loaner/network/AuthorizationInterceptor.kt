package com.example.loaner.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthorizationInterceptor @Inject constructor(): Interceptor {

    companion object {
        var authToken: String? = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("TAG", "intercept: $authToken")
        authToken?.let {
            request.newBuilder()
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobyIsImV4cCI6MTYwOTI1MTU0MX0.tdGSM-8ar1g5cX4cURCiyPxCTLvVB98IZwc7cCKi1OKFk0RPHY1DG8jx-t-SPNrYxbcKUTt1_OLMxE2jrewAHA")
                .build()
        }

        request.headers().names().forEach {
            Log.d("TAG", "Header ${it} ${request.headers().get(it)}")
        }
        return chain.proceed(request)
    }
}
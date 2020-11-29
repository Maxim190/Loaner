package com.example.loaner.di

import com.example.loaner.api.AuthService
import com.example.loaner.api.LoanService
import com.example.loaner.network.AuthorizationInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    fun provideLoginRetrofitService(interceptor: AuthorizationInterceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(Interceptor {
                        it.request().newBuilder()
                                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobyIsImV4cCI6MTYwOTI1MTU0MX0.tdGSM-8ar1g5cX4cURCiyPxCTLvVB98IZwc7cCKi1OKFk0RPHY1DG8jx-t-SPNrYxbcKUTt1_OLMxE2jrewAHA")
                                .build()
                        it.proceed(it.request())
                    })
                    .build())
            .build()
    }

    @Provides
    fun getAuthService(retrofit: Retrofit): AuthService =
         retrofit.create(AuthService::class.java)

    @Provides
    fun getLoanService(retrofit: Retrofit): LoanService =
            retrofit.create(LoanService::class.java)
}
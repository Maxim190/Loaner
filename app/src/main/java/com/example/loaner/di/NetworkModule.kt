package com.example.loaner.di

import com.example.loaner.App.Companion.BASE_URL
import com.example.loaner.api.AuthService
import com.example.loaner.api.LoanService
import com.example.loaner.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideLoginRetrofitService(interceptor: AuthorizationInterceptor): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun getAuthService(retrofit: Retrofit): AuthService =
         retrofit.create(AuthService::class.java)

    @Provides
    fun getLoanService(retrofit: Retrofit): LoanService =
            retrofit.create(LoanService::class.java)
}
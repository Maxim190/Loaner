package com.example.loaner.di

import com.example.loaner.api.AuthService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideLoginRetrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getAuthService(retrofit: Retrofit): AuthService =
         retrofit.create(AuthService::class.java)

}
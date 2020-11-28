package com.example.loaner.api

import com.example.loaner.repository.data.Auth
import com.example.loaner.repository.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthService {

    @POST("/login")
    fun login(@Body auth: Auth): Call<String>

    @POST("/registration")
    fun register(@Body auth: Auth): Call<RegisterResponse>
}
package com.example.loaner.api

import com.example.loaner.repository.data.Conditions
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoanService {
    @POST("/loans")
    fun createLoan(@Body data: LoanRequest): Call<LoanResponse>

    @GET()
    fun getLoan(id: Int)

    @GET()
    fun getLoans()

    @GET("/loans/conditions")
    fun getConditions(): Call<Conditions>
}
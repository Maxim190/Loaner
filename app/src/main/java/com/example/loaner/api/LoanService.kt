package com.example.loaner.api

import com.example.loaner.repository.data.Conditions
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoanService {
    @POST("/loans")
    fun createLoan(@Body data: LoanRequest): Call<LoanData>

    @GET("/loans/{id}")
    fun getLoan(@Path("id") id: Int): Call<LoanData>

    @GET("/loans/all")
    fun getLoans(): Call<List<LoanData>>

    @GET("/loans/conditions")
    fun getConditions(): Call<Conditions>
}
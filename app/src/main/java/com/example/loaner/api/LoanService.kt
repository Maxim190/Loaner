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

    //@Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJobyIsImV4cCI6MTYwOTI1MTU0MX0.tdGSM-8ar1g5cX4cURCiyPxCTLvVB98IZwc7cCKi1OKFk0RPHY1DG8jx-t-SPNrYxbcKUTt1_OLMxE2jrewAHA")
    @GET("/loans/conditions")
    fun getConditions(): Call<Conditions>
}
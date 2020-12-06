package com.example.loaner.repository.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoanRequest(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("firstName")
        val firstName: String,
        @SerializedName("lastName")
        val lastName: String,
        @SerializedName("percent")
        val percent: Double,
        @SerializedName("period")
        val period: Int,
        @SerializedName("phoneNumber")
        val phoneNumber: String
)
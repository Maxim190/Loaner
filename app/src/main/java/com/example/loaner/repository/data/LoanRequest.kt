package com.example.loaner.repository.data

data class LoanRequest(
        val amount: Int,
        val firstName: String,
        val lastName: String,
        val percent: Double,
        val period: Int,
        val phoneNumber: String
)
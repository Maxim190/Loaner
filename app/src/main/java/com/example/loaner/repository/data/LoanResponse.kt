package com.example.loaner.repository.data

data class LoanResponse(
    val name: String,
    val lastName: String,
    val amount: Int,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val date: String,
    val id: Int,
    val state: String
)
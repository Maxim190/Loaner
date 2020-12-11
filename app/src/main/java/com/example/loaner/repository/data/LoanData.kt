package com.example.loaner.repository.data

import java.util.*

data class LoanData(
    val firstName: String,
    val lastName: String,
    val amount: Int,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val date: Date,
    val id: Int,
    val state: String
)
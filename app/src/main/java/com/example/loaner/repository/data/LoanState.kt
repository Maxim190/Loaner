package com.example.loaner.repository.data

enum class LoanState(var status: String) {
    APPROVED("APPROVED"),
    REGISTERED("REGISTERED"),
    REJECTED("REJECTED")
}
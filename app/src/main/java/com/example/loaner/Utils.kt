package com.example.loaner

import java.util.*

fun parseDate(date: Date): String {
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    return "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.YEAR)}"
}
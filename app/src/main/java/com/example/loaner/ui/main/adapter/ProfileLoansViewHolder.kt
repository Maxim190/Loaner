package com.example.loaner.ui.main.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.R
import com.example.loaner.repository.data.LoanData

class ProfileLoansViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val amountView = view.findViewById<TextView>(R.id.profile_card_amount)
    private val percentView = view.findViewById<TextView>(R.id.profile_cart_percent)
    private val periodView = view.findViewById<TextView>(R.id.profile_card_period)

    fun bind(data: LoanData) {
        amountView.text = data.amount.toString()
        percentView.text = data.percent.toString()
        periodView.text = data.period.toString()
    }
}
package com.example.loaner.ui.history.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.R
import com.example.loaner.parseDate
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.LoanState

class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val nameView = view.findViewById<TextView>(R.id.history_name)
    private val lastNameView = view.findViewById<TextView>(R.id.history_last_name)
    private val phoneView = view.findViewById<TextView>(R.id.history_phone)
    private val amountView = view.findViewById<TextView>(R.id.history_amount)
    private val percentView = view.findViewById<TextView>(R.id.history_percent)
    private val periodView = view.findViewById<TextView>(R.id.history_period)
    private val dateView = view.findViewById<TextView>(R.id.history_date)
    private val colorView = view.findViewById<View>(R.id.color_block)

    private val colorApproved: Int = ResourcesCompat.getColor(view.resources, R.color.green, null)
    private val colorRejected: Int = ResourcesCompat.getColor(view.resources, R.color.red, null)
    private val colorRegistered: Int = ResourcesCompat.getColor(view.resources, R.color.gray, null)

    fun bind(data: LoanData) {
        nameView.text = data.firstName
        lastNameView.text = data.lastName
        phoneView.text = data.phoneNumber
        amountView.text = data.amount.toString()
        percentView.text = data.percent.toString()
        periodView.text = data.period.toString()
        dateView.text = parseDate(data.date)

        val color = when(data.state) {
            LoanState.APPROVED.status -> colorApproved
            LoanState.REJECTED.status -> colorRejected
            else -> colorRegistered
        }
        colorView.setBackgroundColor(color)
    }
}
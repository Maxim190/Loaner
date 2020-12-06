package com.example.loaner.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.R
import com.example.loaner.repository.data.LoanData

class ProfileLoansAdapter: RecyclerView.Adapter<ProfileLoansViewHolder>() {

    interface ClickListener{
        fun onClick(itemClicked: LoanData)
    }

    val data = mutableListOf<LoanData>()
    var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ProfileLoansViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.holder_recycler_profile, parent, false))

    override fun onBindViewHolder(holder: ProfileLoansViewHolder, position: Int) {
        holder.bind(data[position])
        clickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.onClick(data[position])
            }
        }
    }

    override fun getItemCount() = data.size
}
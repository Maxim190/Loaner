package com.example.loaner.ui.loan

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.loaner.R
import com.example.loaner.repository.data.LoanState

class ResultLoanFragment: Fragment(R.layout.fragment_success) {

    companion object {
        const val LOAN_STATE = "loanState"
    }

    lateinit var backColorView: FrameLayout
    lateinit var headerTextView: TextView
    lateinit var additionalTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val state = arguments?.getString(LOAN_STATE)?.let { LoanState.valueOf(it) }
        val id = arguments?.getInt(LoanDetailedFragment.LOAD_ID_BUNDLE)

        backColorView = view.findViewById(R.id.frame_layout)
        headerTextView = view.findViewById(R.id.header)
        additionalTextView = view.findViewById(R.id.info)
        when (state) {
            LoanState.REJECTED -> {
                setInfo(R.color.red, R.string.rejected_header, R.string.rejected_additional_info)
            }
            LoanState.REGISTERED -> {
                setInfo(R.color.gray, R.string.registered_header, R.string.registered_additional_info)
            }
        }

        view.findViewById<Button>(R.id.loan_inf_btn).setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, LoanDetailedFragment().apply {
                            arguments = bundleOf(LoanDetailedFragment.LOAD_ID_BUNDLE to id) })
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    private fun setInfo(colorId: Int, headerTextId: Int, additionalTextId: Int) {
        backColorView.setBackgroundColor(ResourcesCompat.getColor(resources, colorId, null))
        headerTextView.text = resources.getText(headerTextId)
        additionalTextView.text = resources.getText(additionalTextId)
    }
}
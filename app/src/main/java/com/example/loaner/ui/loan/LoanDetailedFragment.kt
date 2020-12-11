package com.example.loaner.ui.loan

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.parseDate
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.main.ProfileFragment
import com.example.loaner.vm.LoanViewModel
import javax.inject.Inject

class LoanDetailedFragment: Fragment(R.layout.fragment_loan_detailed) {

    companion object {
        const val LOAD_ID_BUNDLE = "loanId"
    }

    @Inject
    lateinit var viewModel: LoanViewModel

    lateinit var nameView: TextView
    lateinit var lastNameView: TextView
    lateinit var phoneView: TextView
    lateinit var amountView: TextView
    lateinit var percentView: TextView
    lateinit var periodView: TextView
    lateinit var dateView: TextView
    lateinit var stateView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        nameView = view.findViewById(R.id.detailed_first_name)
        lastNameView = view.findViewById(R.id.detailed_last_name)
        phoneView = view.findViewById(R.id.detailed_phone)
        amountView = view.findViewById(R.id.detailed_amount)
        percentView = view.findViewById(R.id.detailed_percent)
        periodView = view.findViewById(R.id.detailed_period)
        dateView = view.findViewById(R.id.detailed_date)
        stateView = view.findViewById(R.id.detailed_state)

        view.findViewById<Button>(R.id.back_to_main).setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .addToBackStack(null)
                        .commit()
            }
        }

        val loanId = arguments?.getInt(LOAD_ID_BUNDLE)
        loanId?.let {
            loadLoan(it)
        }
    }

    private fun loadLoan(id: Int) {
        viewModel.getLoanByIdLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    if (it.value != null) {
                        fillFields(it.value)
                    }
                }
                is ResultData.Error -> {}
            }
        })
        viewModel.getLoanById(id)
    }

    private fun fillFields(loanData: LoanData) {
        nameView.text = loanData.firstName
        lastNameView.text = loanData.lastName
        phoneView.text = loanData.phoneNumber
        amountView.text = loanData.amount.toString()
        percentView.text = loanData.percent.toString()
        periodView.text = loanData.period.toString()
        dateView.text = parseDate(loanData.date)
        stateView.text = loanData.state
    }
}
package com.example.loaner.ui.loan

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.main.LoanViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NewLoanFragment: Fragment(R.layout.fragment_new_loan) {

    @Inject
    lateinit var loanViewModel: LoanViewModel

    @Inject
    lateinit var conditionsViewModel: ConditionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<EditText>(R.id.first_name_view)
        val lastName = view.findViewById<EditText>(R.id.last_name_view)
        val amount = view.findViewById<TextView>(R.id.amount_view)
        val percent = view.findViewById<TextView>(R.id.percent_view)
        val period = view.findViewById<TextView>(R.id.period_view)
        val phoneNum = view.findViewById<EditText>(R.id.phone_view)

        conditionsViewModel.conditionsLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultData.Success -> {
                    percent.text = it.value?.percent.toString()
                    period.text = it.value?.period.toString()
                    amount.text = it.value?.maxAmount.toString()
                }
                is ResultData.Error -> {
                    Toast.makeText(context, "Something was wrong", Toast.LENGTH_LONG).show()
                }
            }
        })
        conditionsViewModel.getConditions()

        loanViewModel.createLoanLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultData.Success -> {
                    Toast.makeText(context, "Loan was accepted!!", Toast.LENGTH_LONG).show()
                    activity?.onBackPressed()
                }
                is ResultData.Error -> {
                    Toast.makeText(context, "Something was wrong", Toast.LENGTH_LONG).show()
                }
            }
        })

        view.findViewById<Button>(R.id.apply_button).setOnClickListener {
            val data =
                LoanRequest(
                        amount.text.toString().toInt(),
                        name.text.toString(),
                        lastName.text.toString(),
                        percent.text.toString().toDouble(),
                        period.text.toString().toInt(),
                        phoneNum.text.toString()
                )
            loanViewModel.createLoan(data)
        }
    }
}
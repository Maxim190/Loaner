package com.example.loaner.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.LoanState
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.loan.LoanDetailedFragment
import com.example.loaner.ui.loan.NewLoanFragment
import com.example.loaner.ui.main.adapter.ProfileLoansAdapter
import com.example.loaner.vm.LoanViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var viewModel: LoanViewModel
    private lateinit var loansApprovedAdapter: ProfileLoansAdapter
    private lateinit var loansRegisteredAdapter: ProfileLoansAdapter
    private lateinit var registeredProgressBar: ProgressBar
    private lateinit var approvedProgressBar: ProgressBar
    private lateinit var loansRegisteredLabel: TextView
    private lateinit var loansApprovedLabel: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.create_loan).setOnClickListener {
            openFragment(NewLoanFragment())
        }

        loansRegisteredLabel = view.findViewById(R.id.label_no_registered_loans)
        loansApprovedLabel = view.findViewById(R.id.label_no_approved_loans)
        registeredProgressBar = view.findViewById(R.id.registered_progress_bar)
        approvedProgressBar = view.findViewById(R.id.approved_progress_bar)
        val recyclerApproved  = view.findViewById<RecyclerView>(R.id.recycler_view_approved)
        val recyclerRegistered  = view.findViewById<RecyclerView>(R.id.recycler_view_registered)
        setRecyclers(recyclerApproved, recyclerRegistered)
    }

    private fun setRecyclers(recyclerViewApproved: RecyclerView, recyclerViewRegistered: RecyclerView) {
        val clickListener = object: ProfileLoansAdapter.ClickListener {
            override fun onClick(itemClicked: LoanData) {
                openFragment(LoanDetailedFragment().apply {
                    arguments = Bundle().apply {
                        putInt(LoanDetailedFragment.LOAD_ID_BUNDLE, itemClicked.id)
                    }
                })
            }
        }
        loansApprovedAdapter = ProfileLoansAdapter().apply {
            this.clickListener = clickListener
        }
        recyclerViewApproved.apply {
            adapter = loansApprovedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        loansRegisteredAdapter = ProfileLoansAdapter().apply {
            this.clickListener = clickListener
        }
        recyclerViewRegistered.apply {
            adapter = loansRegisteredAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        setLoansList()
    }

    private fun setLoansList() {
        viewModel.getLoansLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    if (it.value != null) {
                        setApprovedLoans(it.value[LoanState.APPROVED])
                        setRegisteredLoans(it.value[LoanState.REGISTERED])
                    }
                }
                is ResultData.Error -> {
                }
            }
        })
        viewModel.getLoans()
    }

    private fun setRegisteredLoans(data: List<LoanData>?) {
        val visibility = if (!data.isNullOrEmpty()) {
            loansRegisteredAdapter.data.addAll(data)
            loansRegisteredAdapter.notifyDataSetChanged()
            View.GONE
        }
        else {
            View.VISIBLE
        }
        loansRegisteredLabel.visibility = visibility
        registeredProgressBar.visibility = visibility
    }

    private fun setApprovedLoans(data: List<LoanData>?) {
        val visibility = if (!data.isNullOrEmpty()) {
            loansApprovedAdapter.data.addAll(data)
            loansApprovedAdapter.notifyDataSetChanged()
            View.GONE
        }
        else {
            View.VISIBLE
        }
        loansApprovedLabel.visibility = visibility
        approvedProgressBar.visibility = visibility
    }

    private fun openFragment(fragment: Fragment) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

}
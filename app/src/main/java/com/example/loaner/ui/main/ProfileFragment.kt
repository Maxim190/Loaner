package com.example.loaner.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.loan.LoanDetailedFragment
import com.example.loaner.ui.loan.NewLoanFragment
import com.example.loaner.ui.main.adapter.ProfileLoansAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var viewModel: LoanViewModel
    private lateinit var loansAdapter: ProfileLoansAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.create_loan).setOnClickListener {
            openFragment(NewLoanFragment())
        }

        val recycler  = view.findViewById<RecyclerView>(R.id.recycler_view)
        setRecycler(recycler)
    }

    private fun setRecycler(recyclerView: RecyclerView) {
        loansAdapter = ProfileLoansAdapter().apply {
            clickListener = object: ProfileLoansAdapter.ClickListener {
                override fun onClick(itemClicked: LoanData) {
                    openFragment(LoanDetailedFragment().apply {
                        arguments = Bundle().apply {
                            putInt(LoanDetailedFragment.LOAD_ID_BUNDLE, itemClicked.id)
                        }
                    })
                }
            }
        }
        recyclerView.apply {
            adapter = loansAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        setLoansList()
    }

    private fun setLoansList() {
        viewModel.getLoansLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultData.Success -> {
                    if (it.value != null) {
                        loansAdapter.data.addAll(it.value)
                        loansAdapter.notifyDataSetChanged()
                    }
                }
                is ResultData.Error -> {}
            }
        })
        viewModel.getLoans()
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
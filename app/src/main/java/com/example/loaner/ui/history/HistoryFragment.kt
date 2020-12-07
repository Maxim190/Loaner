package com.example.loaner.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.vm.LoanViewModel
import javax.inject.Inject

class HistoryFragment: Fragment(R.layout.fragment_history) {

    @Inject
    lateinit var viewModel: LoanViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)


    }
}
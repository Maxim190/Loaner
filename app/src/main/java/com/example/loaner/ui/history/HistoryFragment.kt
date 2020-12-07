package com.example.loaner.ui.history

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.history.adapter.HistoryAdapter
import com.example.loaner.vm.LoanViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HistoryFragment: Fragment(R.layout.fragment_history) {

    @Inject
    lateinit var viewModel: LoanViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        val toolBar = view.findViewById<Toolbar>(R.id.toolbar)
        toolBar.title = resources.getString(R.string.history)
        toolBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val adapter = HistoryAdapter()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recycler = view.findViewById<RecyclerView>(R.id.history_recycler)
        recycler.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        val progressBar = view.findViewById<ProgressBar>(R.id.progressbar)
        viewModel.getRawLoansLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is ResultData.Success -> {
                    it.value?.let { loans ->
                        adapter.data.addAll(loans)
                        adapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }
                }
                is ResultData.Error -> {}
            }
        })
        viewModel.getRawLoans()
    }
}
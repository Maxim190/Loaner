package com.example.loaner.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.repository.LoanRepository
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanResponse
import com.example.loaner.repository.data.ResultData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoanViewModel @Inject constructor(val loanRepository: LoanRepository): ViewModel() {

    val createLoanLiveData = MutableLiveData<ResultData<LoanResponse>>()

    fun createLoan(loanRequest: LoanRequest) {
        Log.d("TAG", "createLoan: $loanRequest")
        viewModelScope.launch {
            loanRepository.createLoan(loanRequest)
                .catch {
                    createLoanLiveData.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        createLoanLiveData.value = ResultData.Error(Throwable())
                    }
                    else {
                        createLoanLiveData.value = ResultData.Success(it)
                    }
                }

        }
    }
}
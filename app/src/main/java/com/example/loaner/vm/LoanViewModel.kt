package com.example.loaner.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.LoanState
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.main.LoanManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoanViewModel @Inject constructor(
    private val loanManager: LoanManager
): ViewModel() {

    val createLoanLiveData = MutableLiveData<ResultData<LoanData>>()
    val getLoanByIdLiveData = MutableLiveData<ResultData<LoanData>>()
    val getSortedLoansLiveData = MutableLiveData<ResultData<Map<LoanState, List<LoanData>>>>()
    val getRawLoansLiveData = MutableLiveData<ResultData<List<LoanData>>>()

    fun createLoan(loanRequest: LoanRequest) {
        viewModelScope.launch {
            loanManager.createLoan(loanRequest)
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

    fun getLoanById(id: Int) {
        viewModelScope.launch {
            loanManager.getLoanById(id)
                .catch {
                    getLoanByIdLiveData.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        getLoanByIdLiveData.value = ResultData.Error(Throwable())
                    }
                    else {
                        getLoanByIdLiveData.value = ResultData.Success(it)
                    }
                }
        }
    }

    fun getSortedLoans() {
        viewModelScope.launch {
            loanManager.getSortedLoans()
                .catch {
                    getSortedLoansLiveData.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        getSortedLoansLiveData.value = ResultData.Error(Throwable())
                    }
                    else {
                        getSortedLoansLiveData.value = ResultData.Success(it)
                    }
                }
        }
    }

    fun getRawLoans() {
        viewModelScope.launch {
            loanManager.getRawLoans()
                    .catch {
                        getSortedLoansLiveData.value = ResultData.Error(it)
                    }
                    .collect {
                        if (it == null) {
                            getRawLoansLiveData.value = ResultData.Error(Throwable())
                        }
                        else {
                            getRawLoansLiveData.value = ResultData.Success(it)
                        }
                    }
        }
    }
}
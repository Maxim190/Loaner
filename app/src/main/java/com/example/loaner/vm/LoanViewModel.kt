package com.example.loaner.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.repository.LoanRepository
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
    private val loanRepository: LoanRepository,
    private val loanManager: LoanManager
): ViewModel() {

    val createLoanLiveData = MutableLiveData<ResultData<LoanData>>()
    val getLoanByIdLiveData = MutableLiveData<ResultData<LoanData>>()
    val getLoansLiveData = MutableLiveData<ResultData<Map<LoanState, List<LoanData>>>>()

    fun createLoan(loanRequest: LoanRequest) {
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

    fun getLoanById(id: Int) {
        viewModelScope.launch {
            loanRepository.getLoanById(id)
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

    fun getLoans() {
        viewModelScope.launch {
            loanManager.getLoans()
                .catch {
                    getLoansLiveData.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        getLoansLiveData.value = ResultData.Error(Throwable())
                    }
                    else {
                        getLoansLiveData.value = ResultData.Success(it)
                    }
                }
        }
    }
}
package com.example.loaner.ui.loan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.repository.LoanRepository
import com.example.loaner.repository.data.Conditions
import com.example.loaner.repository.data.ResultData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConditionsViewModel @Inject constructor(val repository: LoanRepository): ViewModel() {

    val conditionsLiveData = MutableLiveData<ResultData<Conditions>>()

    fun getConditions() {
        viewModelScope.launch {
            repository.getConditions()
                .catch {
                    conditionsLiveData.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        conditionsLiveData.value = ResultData.Error(Throwable())
                    }
                    else {
                        conditionsLiveData.value = ResultData.Success(it)
                    }
                }
        }
    }
}
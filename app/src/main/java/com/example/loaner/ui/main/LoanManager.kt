package com.example.loaner.ui.main

import com.example.loaner.repository.LoanRepository
import com.example.loaner.repository.data.LoanData
import com.example.loaner.repository.data.LoanState
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoanManager @Inject constructor(private val repository: LoanRepository) {

    private var data = mutableMapOf<LoanState, MutableList<LoanData>>()

    fun getLoans(): Flow<Map<LoanState, List<LoanData>>> {
        return repository.getLoans()
            .map {
                it?.forEach {
                    if (!data.containsKey(LoanState.valueOf(it.state))) {
                        data[LoanState.valueOf(it.state)] = mutableListOf()
                    }
                    data[LoanState.valueOf(it.state)]?.add(it)
                }
                return@map data
            }
    }
}
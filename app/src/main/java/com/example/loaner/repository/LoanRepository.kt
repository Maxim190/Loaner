package com.example.loaner.repository

import android.util.Log
import com.example.loaner.api.LoanService
import com.example.loaner.repository.data.Conditions
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoanRepository @Inject constructor(val loanService: LoanService) {

    fun createLoan(data: LoanRequest): Flow<LoanData?> {
        return callbackFlow {
            loanService.createLoan(data).enqueue(object : Callback<LoanData> {
                override fun onResponse(call: Call<LoanData>, data: Response<LoanData>) {
                    offer(data.body())
                }

                override fun onFailure(call: Call<LoanData>, t: Throwable) {
                    close(t)
                }

            })
            awaitClose { cancel() }
        }
    }

    fun getLoanById(id: Int): Flow<LoanData?> {
        return callbackFlow {
            loanService.getLoan(id).enqueue(object: Callback<LoanData> {
                override fun onResponse(call: Call<LoanData>, response: Response<LoanData>) {
                    offer(response.body())
                }

                override fun onFailure(call: Call<LoanData>, t: Throwable) {
                    close(t)
                }
            })
            awaitClose { cancel() }
        }
    }

    fun getLoans(): Flow<List<LoanData>?> {
        return callbackFlow {
            loanService.getLoans().enqueue(object: Callback<List<LoanData>> {
                override fun onResponse(call: Call<List<LoanData>>, response: Response<List<LoanData>>) {
                    offer(response.body())
                }

                override fun onFailure(call: Call<List<LoanData>>, t: Throwable) {
                    close(t)
                }
            })
            awaitClose { cancel() }
        }
    }

    fun getConditions(): Flow<Conditions?> {
        return callbackFlow {
            loanService.getConditions().enqueue(object : Callback<Conditions> {
                override fun onResponse(call: Call<Conditions>, response: Response<Conditions>) {
                    offer(response.body())
                }

                override fun onFailure(call: Call<Conditions>, t: Throwable) {
                    offer(t)
                }
            })
            awaitClose {close()}
        }
    }
}
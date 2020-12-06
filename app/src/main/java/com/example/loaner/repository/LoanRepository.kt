package com.example.loaner.repository

import android.util.Log
import com.example.loaner.api.LoanService
import com.example.loaner.repository.data.Conditions
import com.example.loaner.repository.data.LoanRequest
import com.example.loaner.repository.data.LoanResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
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

    fun createLoan(data: LoanRequest): Flow<LoanResponse?> {
        return callbackFlow {
            loanService.createLoan(data).enqueue(object : Callback<LoanResponse> {
                override fun onResponse(call: Call<LoanResponse>, response: Response<LoanResponse>) {
                    offer(response.body())
                }

                override fun onFailure(call: Call<LoanResponse>, t: Throwable) {
                    Log.d("TAG", "Create fails $t")
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
                    Log.d("TAG", "conditions onResponse: ${response.body()} ${response.code()}")
                    offer(response.body())
                }

                override fun onFailure(call: Call<Conditions>, t: Throwable) {
                    Log.d("TAG", "conditions onFil}")
                    offer(t)
                }
            })
            awaitClose {close()}
        }
    }
}
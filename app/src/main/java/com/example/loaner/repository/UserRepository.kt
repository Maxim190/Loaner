package com.example.loaner.repository

import android.util.Log
import com.example.loaner.api.AuthService
import com.example.loaner.repository.data.Auth
import com.example.loaner.repository.data.RegisterResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRepository @Inject constructor(private val authService: AuthService) {

    val TAG = "TAG"

    suspend fun login(name: String, password: String): Flow<String?> {
        return callbackFlow {
            authService.login(Auth(name, password)).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    offer(response.body())
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    offer(t)
                }
            })
            awaitClose { cancel() }
        }
    }

    fun register(name: String, password: String): Flow<RegisterResponse?> {
        return callbackFlow {
            authService.register(Auth(name, password)).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    offer(response.body())
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    offer(t)
                }

            })
            awaitClose { cancel() }
        }
    }

}
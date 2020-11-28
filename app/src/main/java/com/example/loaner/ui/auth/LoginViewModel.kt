package com.example.loaner.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.App.Companion.TAG
import com.example.loaner.api.ResultData
import com.example.loaner.repository.UserRepository
import com.example.loaner.repository.data.RegisterResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val loginResponse = MutableLiveData<ResultData<String>>()
    val registerResponse = MutableLiveData<ResultData<RegisterResponse>>()

    fun login(name: String, password: String) {
        viewModelScope.launch {
            userRepository.login(name, password)
                .catch {
                    loginResponse.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        loginResponse.value = ResultData.Error(Throwable())
                    }
                    else {
                        loginResponse.value = ResultData.Success(it)
                    }
                }
        }
    }

    fun register(name: String, password: String) {
        viewModelScope.launch {
            userRepository.register(name, password)
                .catch {
                    registerResponse.value = ResultData.Error(it)
                }
                .collect {
                    if (it == null) {
                        registerResponse.value = ResultData.Error(Throwable())
                    }
                    else {
                        registerResponse.value = ResultData.Success(it)
                    }
                }
        }
    }
}
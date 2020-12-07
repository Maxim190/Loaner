package com.example.loaner.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loaner.repository.LoginManager
import com.example.loaner.repository.data.ResultData
import com.example.loaner.repository.UserRepository
import com.example.loaner.repository.data.RegisterResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor(private val loginManager: LoginManager, val userRepository: UserRepository): ViewModel() {

    val loginResponse = MutableLiveData<Boolean>()
    val registerResponse = MutableLiveData<ResultData<RegisterResponse>>()

    fun login(name: String, password: String) {
        viewModelScope.launch {
            loginManager.login(name, password)
                .catch {
                    loginResponse.value = false
                }
                .collect {
                    loginResponse.value = it
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
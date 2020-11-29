package com.example.loaner.repository

import androidx.lifecycle.MutableLiveData
import com.example.loaner.network.AuthorizationInterceptor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

class LoginManager @Inject constructor(val userRepository: UserRepository) {

    @ExperimentalCoroutinesApi
    fun login (name: String, password: String): Flow<Boolean> {
        return callbackFlow {
            userRepository.login(name, password)
                    .catch { offer(false) }
                    .collect {
                        if (!it.isNullOrBlank()) {
                            AuthorizationInterceptor.authToken = it
                            offer(true)
                        }
                        else {
                            offer(false)
                        }
                    }
            awaitClose { close() }
        }
    }
}
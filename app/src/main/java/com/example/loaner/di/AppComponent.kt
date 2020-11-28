package com.example.loaner.di

import com.example.loaner.ui.MainActivity
import com.example.loaner.ui.auth.LoginFragment
import dagger.Component

@Component (modules = [NetworkModule::class])
interface AppComponent {
    fun inject(fragment: LoginFragment)
    fun inject(activity: MainActivity)
}
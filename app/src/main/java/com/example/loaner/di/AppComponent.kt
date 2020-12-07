package com.example.loaner.di

import com.example.loaner.ui.MainActivity
import com.example.loaner.ui.auth.LoginFragment
import com.example.loaner.ui.loan.LoanDetailedFragment
import com.example.loaner.ui.loan.NewLoanFragment
import com.example.loaner.ui.history.HistoryFragment
import com.example.loaner.ui.main.ProfileFragment
import dagger.Component

@Component (modules = [NetworkModule::class])
interface AppComponent {
    fun inject(fragment: LoginFragment)
    fun inject(fragment: NewLoanFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: HistoryFragment)
    fun inject(fragment: LoanDetailedFragment)
    fun inject(activity: MainActivity)
}
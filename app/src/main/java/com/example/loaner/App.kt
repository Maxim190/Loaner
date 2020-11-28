package com.example.loaner

import android.app.Application
import com.example.loaner.di.DaggerAppComponent

class App: Application() {

    companion object {
        const val TAG = "TAG"
    }

    val appComponent = DaggerAppComponent.create()
}
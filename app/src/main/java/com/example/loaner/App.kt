package com.example.loaner

import android.app.Application
import com.example.loaner.di.DaggerAppComponent

class App: Application() {

    companion object {
        const val TAG = "TAG"
        const val BASE_URL = "http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com/"
    }

    val appComponent = DaggerAppComponent.create()
}
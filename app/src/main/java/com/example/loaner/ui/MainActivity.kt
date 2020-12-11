package com.example.loaner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loaner.R

class MainActivity: AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MainFragment())
                .addToBackStack(null)
                .commit()
    }


    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
        if (supportFragmentManager.backStackEntryCount <= 1) {
            super.onBackPressed()
        }
    }
}
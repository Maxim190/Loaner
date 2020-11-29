package com.example.loaner.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.loaner.R
import com.example.loaner.ui.auth.LoginFragment

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
        Log.d("TAG", "HERE: ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount <= 1) {
            super.onBackPressed()
        }
    }
}
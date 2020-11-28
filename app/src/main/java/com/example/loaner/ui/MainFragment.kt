package com.example.loaner.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.loaner.R
import com.example.loaner.ui.auth.LoginFragment

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            openFragment(true)
        }

        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            openFragment(false)
        }
    }

    private fun openFragment(isLogin: Boolean) {
        val bundleValue = if (isLogin) {
            LoginFragment.MODE_LOGIN
        }
        else {
            LoginFragment.MODE_REGISTRATION
        }

        (activity as MainActivity).openFragment(
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(LoginFragment.MODE_BUNDLE_NAME, bundleValue)
                }
            }
        )
    }
}
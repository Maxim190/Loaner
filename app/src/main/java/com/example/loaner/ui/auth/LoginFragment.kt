package com.example.loaner.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.loaner.App
import com.example.loaner.R
import com.example.loaner.repository.data.ResultData
import com.example.loaner.ui.MainFragment
import com.example.loaner.ui.main.ProfileFragment
import com.example.loaner.vm.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginFragment: Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    companion object {
        const val MODE_BUNDLE_NAME = "mode"
        const val MODE_LOGIN = "login"
        const val MODE_REGISTRATION = "registration"
    }

    lateinit var nameView: EditText
    lateinit var passwordView: EditText
    lateinit var msgView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.applicationContext as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        val isRegistrationMode = arguments?.getString(MODE_BUNDLE_NAME) == MODE_REGISTRATION

        nameView = view.findViewById(R.id.auth_name_view)
        passwordView = view.findViewById(R.id.auth_password_view)
        msgView = view.findViewById(R.id.error_msg)
        val fragmentLabel = view.findViewById<TextView>(R.id.label)
        val authButton = view.findViewById<Button>(R.id.auth_button)

        if (isRegistrationMode) {
            resources.getString(R.string.sign_up).apply {
                fragmentLabel.text = this
                authButton.text = this
            }
            loginViewModel.registerResponse.observe(viewLifecycleOwner, {
                when (it) {
                    is ResultData.Success -> {
                        Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                        openFragment(MainFragment())
                    }
                    is ResultData.Error -> {
                        Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            authButton.setOnClickListener {
                if (!fieldsAreBlank()) {
                    loginViewModel.register(nameView.text.toString(), passwordView.text.toString())
                }
            }
        }
        else {
            loginViewModel.loginResponse.observe(viewLifecycleOwner, {
                if (it) {
                    openFragment(ProfileFragment())
                }
                else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
            })
            authButton.setOnClickListener {
                if(!fieldsAreBlank()) {
                    loginViewModel.login(nameView.text.toString(), passwordView.text.toString())
                }
            }
        }
    }

    fun openFragment(fragment: Fragment) {
        activity?.let{
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun fieldsAreBlank(): Boolean {
        val name = nameView.text
        val password = passwordView.text
        return if (name.isNullOrBlank() || password.isNullOrBlank()) {
            msgView.visibility = View.VISIBLE
            true
        }
        else {
            msgView.visibility = View.INVISIBLE
            false
        }
    }
}
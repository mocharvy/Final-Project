package com.programmer.finalproject.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ActivityLoginBinding
import com.programmer.finalproject.model.login.LoginRequest
import com.programmer.finalproject.ui.fragment.auth.ForgotPasswordActivity
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isDataValid()
        loginUser()
        moveToRegister()
        forgotPassword()
        enterAsGuest()

    }

    private fun loginUser() {
        binding.btLogin.setOnClickListener {
            val loginRequest = LoginRequest(
                emailOrPhone = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            login(loginRequest)
        }
    }

    private fun login(loginRequest: LoginRequest) {
        loginViewModel.login(loginRequest)
        showLoading(true)

        loginViewModel.loginResponse.observe(this) {
            showLoading(false)
            showToast("Login berhasil")

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginViewModel.errorState.observe(this) { throwable ->
            showLoading(false)
            showToast("Login gagal: ${throwable.message}")
        }

    }

    private fun moveToRegister() {
        binding.tvRegHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun forgotPassword() {
        binding.tvForgot.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun enterAsGuest() {
        binding.tvGuest.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isDataValid() {
        val emailEt = binding.etEmail
        val passwordEt = binding.etPassword
        val btLogin = binding.btLogin

        emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            @Suppress("DEPRECATION")
            override fun afterTextChanged(s: Editable?) {
                if (emailEt.emailValid() && passwordEt.isPasswordValid()) {
                    btLogin.isEnabled = true
                    btLogin.setBackgroundColor(resources.getColor(R.color.dark_blue))
                } else {
                    btLogin.isEnabled = false
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_grey))
                }
            }
        })

        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @Suppress("DEPRECATION")
            override fun afterTextChanged(p0: Editable?) {
                if (emailEt.emailValid() && passwordEt.isPasswordValid()) {
                    btLogin.isEnabled = true
                    btLogin.setBackgroundColor(resources.getColor(R.color.dark_blue))
                } else {
                    btLogin.isEnabled = false
                    btLogin.setBackgroundColor(resources.getColor(R.color.light_grey))
                }
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
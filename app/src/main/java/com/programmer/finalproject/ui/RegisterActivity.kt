package com.programmer.finalproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ActivityRegisterBinding
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.ui.fragment.auth.RegisterViewModel
import com.programmer.finalproject.ui.fragment.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToLogin()
        register()

    }

    private fun register() {
        binding.btRegister.setOnClickListener {

            val emailEditText = binding.etEmail
            val passwordEditText = binding.etPassword

            if (!emailEditText.isEmailValid(emailEditText.text.toString())) {
                Toast.makeText(this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!passwordEditText.isPasswordValid()) {
                Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val userEmail = binding.etEmail.text.toString()
            val userPassword = binding.etPassword.text.toString()
            val userName = binding.etUsername.text.toString()
            val userPhone = binding.etPhone.text.toString()

            val registerRequest = RegisterRequest(
                name = userName,
                email = userEmail,
                password = userPassword,
                no_telp = userPhone,
            )
            registerUser(registerRequest)
        }
    }

    private fun registerUser(registerRequest: RegisterRequest) {
        registerViewModel.register(registerRequest)
        showLoading(true)

        registerViewModel.registerResponse.observe(this) {
            showLoading(false)
            showToast("Silahkan cek email anda untuk verifikasi.")

            val intent = Intent(this, OtpActivity::class.java)
            startActivity(intent)
        }

        registerViewModel.errorState.observe(this) { throwable ->
            showLoading(false)
            showToast("Register gagal: ${throwable.message}")
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveToLogin() {
        binding.tvLoginHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
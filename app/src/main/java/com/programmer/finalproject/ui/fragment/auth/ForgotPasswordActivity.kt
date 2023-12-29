package com.programmer.finalproject.ui.fragment.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.ActivityForgotPasswordBinding
import com.programmer.finalproject.databinding.DialogEditProfileBinding
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import com.programmer.finalproject.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendEmail()
        backToLogin()
    }

    private fun sendEmail() {
        with(binding) {
            btnKirimkan.setOnClickListener {
                val resetPasswordRequest = ResetPasswordRequest(
                    etEmail.text.toString()
                )
                authViewModel.resetPassword(resetPasswordRequest)
                authViewModel.loadingState.observe(this@ForgotPasswordActivity) { isLoading ->
                    pb.isVisible = isLoading
                }

                authViewModel.verified.observe(this@ForgotPasswordActivity) {
                    if (it) {
                        etEmail.setText("")
                        showDialog()
                    }
                }

                authViewModel.isError.observe(this@ForgotPasswordActivity) {
                    if (it)
                        tilEmail.error = "Email Not Found"
                }

            }

        }
    }

    private fun showDialog() {
        val successChange =
            DialogEditProfileBinding.inflate(LayoutInflater.from(this))
        val successChangeDialogBuilder =
            AlertDialog.Builder(this, R.style.RoundedCornerDialog)
                .setView(successChange.root)
        successChangeDialogBuilder.setCancelable(true)
        Glide.with(this)
            .asGif()
            .load(R.raw.succes)
            .into(successChange.ivIcon)

        val showSuccessDialog = successChangeDialogBuilder.show()
        successChange.message.text = getString(R.string.link_sent)

        Handler(Looper.myLooper()!!).postDelayed({
            showSuccessDialog.dismiss()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun backToLogin() {
        binding.backToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
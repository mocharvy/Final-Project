package com.programmer.finalproject.ui.fragment.otp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.otpview.OTPListener
import com.programmer.finalproject.databinding.ActivityOtpBinding
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.ui.LoginActivity
import com.programmer.finalproject.ui.fragment.auth.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    private val otpViewModel: OtpViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            startCountdownTimer()

            otpView.requestFocusOTP()
            otpView.otpListener = object : OTPListener {
                override fun onInteractionListener() {

                }

                override fun onOTPComplete(otp: String) {
                    val otpList = otp.map { it.toString() }
                    val otpRequest = OTPRequest(
                        code1 = otpList[0],
                        code2 = otpList[1],
                        code3 = otpList[2],
                        code4 = otpList[3],
                        code5 = otpList[4],
                        code6 = otpList[5]
                    )
                    registerViewModel.registerResponse.observe(this@OtpActivity) {
                        otpViewModel.postOtp("Bearer ${it!!.data.accessToken}", otpRequest)
                    }


                    Toast.makeText(this@OtpActivity, "Verification Success", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@OtpActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }

    private fun startCountdownTimer() {
        object : CountDownTimer(59000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.countdownText.text = "Kirim Ulang dalam $seconds detik"
            }

            override fun onFinish() {
                binding.tvResend.visibility = View.VISIBLE
                binding.tvResend.setOnClickListener {
                    otpViewModel
                    registerViewModel.registerResponse.observe(this@OtpActivity) {
                        otpViewModel.getOtp("Bearer ${it!!.data.accessToken}")
                    }

                    otpViewModel.isError.observe(this@OtpActivity) {
                        if (it) {
                            Toast.makeText(
                                this@OtpActivity,
                                "Failed sending OTP",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@OtpActivity,
                                "OTP sent to your email",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
                binding.countdownText.visibility = View.GONE
            }
        }.start()

        binding.tvResend.visibility = View.GONE
        binding.countdownText.visibility = View.VISIBLE
    }
}
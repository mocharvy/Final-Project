package com.programmer.finalproject.ui.fragment.otp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.otpview.OTPListener
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentOtpBinding
import com.programmer.finalproject.model.otp.OTPRequest
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.auth.RegisterFragment.Companion.ACCESS_TOKEN
import com.programmer.finalproject.ui.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OtpFragment : Fragment() {
    private lateinit var binding : FragmentOtpBinding
    private val otpViewModel: OtpViewModel by viewModels()
    private val registerViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    registerViewModel.registerResponse.observe(viewLifecycleOwner){
                        otpViewModel.postOtp("Bearer ${it!!.data.accessToken}",otpRequest)
                    }


                    Toast.makeText(requireContext(), "Verification Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
                }
            }

        }
    }

    private fun startCountdownTimer() {
        val timer = object : CountDownTimer(59000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.countdownText.text = "Kirim Ulang dalam $seconds detik"
            }

            override fun onFinish() {
                binding.tvResend.visibility = View.VISIBLE
                binding.tvResend.setOnClickListener {
                    otpViewModel
                    registerViewModel.registerResponse.observe(viewLifecycleOwner){
//                        otpViewModel.getOtp("Bearer $ACCESS_TOKEN")

                        otpViewModel.getOtp("Bearer ${it!!.data.accessToken}")
                    }

                    otpViewModel.isError.observe(viewLifecycleOwner){
                        if(it){
                            Toast.makeText(requireContext(), "Gagal Mengirim OTP", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), "OTP berhasil dikirim ke Email Anda", Toast.LENGTH_SHORT).show()

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
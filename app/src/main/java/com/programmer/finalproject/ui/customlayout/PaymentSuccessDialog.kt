package com.programmer.finalproject.ui.customlayout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.databinding.FragmentPaymentSuccessBinding

class PaymentSuccessDialog : DialogFragment() {

    private lateinit var binding: FragmentPaymentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentSuccessBinding.inflate(layoutInflater, container, false)

        goToHome()

        return binding.root
    }

    private fun goToHome() {
        binding.btHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
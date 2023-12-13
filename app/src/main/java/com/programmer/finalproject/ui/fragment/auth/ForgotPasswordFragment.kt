package com.programmer.finalproject.ui.fragment.auth

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.DialogEditProfileBinding
import com.programmer.finalproject.databinding.FragmentForgotPasswordBinding
import com.programmer.finalproject.model.register.RegisterRequest
import com.programmer.finalproject.model.user.password.ResetPasswordRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding : FragmentForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendEmail()

    }

    private fun sendEmail() {
        with(binding) {
            btnKirimkan.setOnClickListener {
                val resetPasswordRequest = ResetPasswordRequest(
                    etEmail.text.toString()
                )
                viewModel.resetPassword(resetPasswordRequest)
                viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
                    pb.isVisible = isLoading
                }
                viewModel.verified.observe(viewLifecycleOwner) {
                    if (it) {
                        etEmail.setText("")
                        showDialog()

                    }
                }
                viewModel.isError.observe(viewLifecycleOwner) {
                    if (it)
                        tilEmail.error = "Email tidak ditemukan"
                }

            }

        }
    }

    private fun showDialog() {
        val successChange =
            DialogEditProfileBinding.inflate(LayoutInflater.from(requireContext()))
        val successChangeDialogBuilder =
            AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
                .setView(successChange.root)
        successChangeDialogBuilder.setCancelable(true)
        Glide.with(requireContext())
            .asGif()
            .load(R.raw.succes)
            .into(successChange.ivIcon)

        val showSuccessDialog = successChangeDialogBuilder.show()
        successChange.message.text = "Email telah dikirimkan ke email anda"

        Handler(Looper.myLooper()!!).postDelayed({
            showSuccessDialog.dismiss()
            findNavController().popBackStack()

        }, 5000)
    }


}
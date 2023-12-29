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
import com.programmer.finalproject.databinding.DialogSuccessRegisterBinding
import com.programmer.finalproject.databinding.FragmentRegisterBinding
import com.programmer.finalproject.model.register.RegisterRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnDaftar.setOnClickListener {
                val nama = kolomNama.text.toString()
                val email = kolomEmail.text.toString()
                val password = kolomKatasandi.text.toString()
                val noTelp = kolomTelp.text.toString()

                if (!isNameValid(nama)) {
                    kolomNama.error = "Nama harus melebihi 3 huruf dan tidak menggunakan angka atau simbol"
                    return@setOnClickListener
                }



                if (!isEmailValid(email)) {
                    kolomEmail.error = "Email tidak valid! Pastikan email Anda memiliki format yang benar (contoh: DevAcademy@gmail.com)"
                    return@setOnClickListener
                }

                if (!isPasswordValid(password)) {
                    kolomKatasandi.error = "Password harus terdiri dari minimal 8 karakter"
                    return@setOnClickListener
                }

                val registerRequest = RegisterRequest(
                    name = nama,
                    email = email,
                    password = password,
                    no_telp = "+62$noTelp",
                )

                viewModel.register(registerRequest)
                viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
                    pb.isVisible = isLoading
                }

                viewModel.verified.observe(viewLifecycleOwner) {
                    if (it) {
                        showSuccessDialog()
                    }
                }

                viewModel.isError.observe(viewLifecycleOwner) { it ->
                    if (it) {
                        Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.registerResponse.observe(viewLifecycleOwner) { register ->
                        if (register != null) {
                            ACCESS_TOKEN = register.data.accessToken
                        }
                    }
                }
            }
        }
    }

    private fun showSuccessDialog() {
        val registerSuccess =
            DialogSuccessRegisterBinding.inflate(layoutInflater)
        val registerDialogBuilder =
            AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
                .setView(registerSuccess.root)
        registerDialogBuilder.setCancelable(true)
        Glide.with(requireContext())
            .asGif()
            .load(R.raw.succes)
            .into(registerSuccess.ivSuccess)

        val showSuccessDialog = registerDialogBuilder.show()

        Handler(Looper.getMainLooper()).postDelayed({
            showSuccessDialog.dismiss()
            //findNavController().navigate(R.id.action_registerFragment_to_otpFragment)
        }, 3000)
    }

    private fun isNameValid(name: String): Boolean {
        return name.length > 3 && name.matches("[a-zA-Z ]+".toRegex())
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.startsWith("+62")
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    companion object {
        var ACCESS_TOKEN = ""
    }
}

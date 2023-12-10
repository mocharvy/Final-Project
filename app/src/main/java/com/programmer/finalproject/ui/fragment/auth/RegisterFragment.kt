package com.programmer.finalproject.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.programmer.finalproject.R
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
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnDaftar.setOnClickListener {
                if (isInputValid()) {
                    registerUser()
                }else{
                    Toast.makeText(requireContext(), "Periksa Inputan Anda ! ", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun registerUser() {
        val registerRequest = RegisterRequest(
            name = binding.kolomNama.text.toString(),
            email = binding.kolomEmail.text.toString(),
            password = binding.kolomKatasandi.text.toString(),
            no_telp = binding.kolomTelp.text.toString(),
        )
        viewModel.register(registerRequest)
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pb.isVisible = isLoading
        }

        viewModel.verified.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Berhasil Register", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Register Gagal", Toast.LENGTH_SHORT).show()
            } else {

            }

        }

    }

    fun isInputValid(): Boolean {
        val nama = binding.kolomNama.text.toString()
        val email = binding.kolomEmail.text.toString()
        val password = binding.kolomKatasandi.text.toString()

        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false
        }

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if (!email.matches(emailPattern.toRegex())) {
            return false
        }

        if (password.length < 8) {
            return false
        }
        return true
    }

}
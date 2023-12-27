package com.programmer.finalproject.ui.fragment.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentLoginBinding
import com.programmer.finalproject.model.login.LoginRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        binding.apply {
            txtDaftardisini.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                val inputEmail = columEmail.text.toString()
                val inputPassword = columPassword1.text.toString()

                if (!isValidEmail(inputEmail)) {
                    columEmail.error = "Email tidak valid! Pastikan email Anda memiliki format yang benar (contoh: DevAcademy@gmail.com)"
                } else if (inputPassword.length < 8) {
                    columPassword1.error = "Password harus terdiri dari minimal 8 karakter"
                } else {
                    login()
                }
            }
            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
            txtMasuktanpalogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
            }
            txtText.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

//    private fun verify() {
//        if (isLoginInfoValid()) {
//            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
//        }
//    }

//    private fun isLoginInfoValid(): Boolean {
//        return sharedPreferences.getBoolean(STATUS_LOGIN, false)
//    }

    private var loginClicks = 0
    private fun login() {

        loginClicks += 1

        val loginRequest = LoginRequest(
            emailOrPhone = binding.columEmail.text.toString(),
            password = binding.columPassword1.text.toString()
        )
        viewModel.login(loginRequest)

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
//            binding.pb.isVisible = isLoading
        }

        viewModel.verified.observe(viewLifecycleOwner) {
            if (it) {

                saveLoginStatus(true)
                //Toast.makeText(requireContext(), "Anda Berhasil Login", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Cek email, untuk verifikasi", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.accountData.observe(viewLifecycleOwner) {
            saveLoginInfo(
                it?.data?.accessToken,
                )
        }
    }

    private fun saveLoginStatus(loginStatus: Boolean) {
        sharedPreferences.edit {
            putBoolean(STATUS_LOGIN, loginStatus)
        }
    }

    private fun saveLoginInfo(accessToken: String?) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, accessToken)

            putInt(USER_ID, id)
            putString(TOKEN, accessToken)
        }
    }


    companion object {
        const val LOGIN_SHARED_PREF = "login_shared_pref"
        const val ACCESS_TOKEN = "access_token"
        var USER_ID = "account_id"
        const val STATUS_LOGIN = "status_login"

        const val TOKEN = ""
    }
}
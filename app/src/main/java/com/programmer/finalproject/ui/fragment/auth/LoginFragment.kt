package com.programmer.finalproject.ui.fragment.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.isVisible
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
//            btn.setOnClickListener {
//                    Toast.makeText(requireContext(), "Anda Berhasil Login", Toast.LENGTH_SHORT).show()

                login()
                //verify()


//            }

        }

//        binding.tvBypass.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
//        }
    }

    private fun verify() {
        if (isLoginInfoValid()) {
            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment)
        }
    }

    private fun isLoginInfoValid(): Boolean {
        return sharedPreferences.getBoolean(STATUS_LOGIN, false)
    }

    var loginClicks = 0
    private fun login() {

        loginClicks += 1

        val loginRequest = LoginRequest(
            emailOrPhone = binding.columEmail.text.toString(),
            password = binding.columPassword.text.toString()
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

            if (id != null) {
                putInt(USER_ID, id.toInt())
                putString(TOKEN, accessToken)
            }
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
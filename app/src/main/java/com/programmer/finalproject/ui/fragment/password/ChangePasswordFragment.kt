package com.programmer.finalproject.ui.fragment.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentChangePasswordBinding
import com.programmer.finalproject.model.user.password.ChangePasswordRequest
import com.programmer.finalproject.model.user.update.ProfileRequest
import com.programmer.finalproject.ui.fragment.akun.AkunViewModel
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val passwordViewModel: PasswordViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangePasswordBinding.inflate(layoutInflater,container,false)

        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.apply {
            binding.btnChangePassword.setOnClickListener {
                authViewModel.token.observe(viewLifecycleOwner){
                    if (it !=null){

                            val changePassword = ChangePasswordRequest(etOldPassword.text.toString(),etNewPassword.text.toString(), etConfrimNewPassword.text.toString())
                            passwordViewModel.changePassword("Bearer $it",changePassword)

                    }

                }
            }
        }
    }


}
package com.programmer.finalproject.ui.fragment.password

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.DialogEditProfileBinding
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
                    if (it != null) {
                        val newPassword = etNewPassword.text.toString()
                        val confirmPassword = etConfrimNewPassword.text.toString()
                        if (newPassword == confirmPassword) {
                            val changePassword = ChangePasswordRequest(newPassword, confirmPassword,etOldPassword.text.toString())
                            passwordViewModel.changePassword("Bearer $it", changePassword)
                            Toast.makeText(requireContext(), "Password Berhasil di Ubah", Toast.LENGTH_SHORT).show()
                            showDialog()
                        } else {
                            Toast.makeText(requireContext(), "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                        }
                    }
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
            successChange.message.text = "Kata Sandi Anda Berhasil di Ubah"

            Handler(Looper.myLooper()!!).postDelayed({
                showSuccessDialog.dismiss()
                findNavController().popBackStack()

            }, 5000)
        }


}
package com.programmer.finalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentEditProfileBinding
import com.programmer.finalproject.model.user.UserDetailResponse
import com.programmer.finalproject.model.user.update.ProfileRequest
import com.programmer.finalproject.ui.fragment.akun.AkunViewModel
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class EditProfileFragment : Fragment() {
    private lateinit var binding : FragmentEditProfileBinding
    private val akunViewModel: AkunViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater,container,false)

        getDetailUser()
        observeUserDetailResponse()
        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.apply {
            binding.btnSaveProfile.setOnClickListener {
                authViewModel.token.observe(viewLifecycleOwner){
                    if (it !=null){
                        val profileRequest = ProfileRequest(etNama.text.toString(),etEmail.text.toString(), etTelepon.text.toString(),etNegara.text.toString(),etKota.text.toString(),"profile.png")

                        akunViewModel.updateProfile("Bearer $it",profileRequest)
                    }

                }
            }
        }

    }

    private fun getDetailUser() {
        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("TOKEN", it)

                akunViewModel.getUserDetail("Bearer $it")
            } else {

                binding.progressBar.visibility = View.GONE

            }
        }
    }
    private fun observeUserDetailResponse() {
        akunViewModel.userDetailResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    hideLoading()
                    val userDetail = result.data!!
                    updateUI(userDetail)
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailKelasFragment", "Error: ${result.message}")
                }
            }
        }
    }
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun updateUI(userDetail: UserDetailResponse) {
        binding.profileBackground.load(userDetail.data.photo)
        binding.etNama.setText(userDetail.data.name)
        binding.etEmail.setText(userDetail.data.email)
        binding.etTelepon.setText(userDetail.data.phoneNumber)
    }
}
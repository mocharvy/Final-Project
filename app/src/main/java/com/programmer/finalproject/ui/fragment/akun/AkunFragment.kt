package com.programmer.finalproject.ui.fragment.akun

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.DialogLogoutBinding
import com.programmer.finalproject.databinding.FragmentAkunBinding
import com.programmer.finalproject.model.user.update.ProfileResponse
import com.programmer.finalproject.ui.LoginActivity
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding

    private val akunViewModel: AkunViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(layoutInflater, container, false)

        getUserDetail()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvProfile.setOnClickListener {
                findNavController().navigate(R.id.action_akunFragment_to_editProfileFragment)
            }
            tvSetting.setOnClickListener {
                findNavController().navigate(R.id.action_akunFragment_to_changePasswordFragment)
            }
            tvHistory.setOnClickListener {
                findNavController().navigate(R.id.action_akunFragment_to_historyPaymentFragment)
            }
            tvLogout.setOnClickListener {
                showDialogLogout()
            }
        }
    }

    private fun showDialogLogout() {
        val logout = DialogLogoutBinding.inflate(LayoutInflater.from(requireContext()))
        val logoutDialogBuilder = AlertDialog.Builder(requireContext(), R.style.RoundedCornerDialog)
            .setView(logout.root)
        logoutDialogBuilder.setCancelable(true)
        val logoutDialog = logoutDialogBuilder.show()
        logout.btnLogout.setOnClickListener {
            loginViewModel.logout()

            logoutDialog.dismiss()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
        logout.btnDismiss.setOnClickListener {
            logoutDialog.dismiss()
        }
    }

    private fun getUserDetail() {
        Log.d("Get User Detail", "getUserDetail fun called")
        loginViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.d("TOKEN", it)
                binding.clAkun.visibility = View.VISIBLE
                binding.toLogin.visibility = View.GONE
                akunViewModel.getUserDetail("Bearer $it")
                observeUserDetailResponse()

                val mustLoginBottomSheet =
                    requireActivity().supportFragmentManager.findFragmentByTag("mustLoginBottomSheet")
                if (mustLoginBottomSheet != null && mustLoginBottomSheet is BottomSheetDialogFragment) {
                    mustLoginBottomSheet.dismiss()
                }
            } else {
                findNavController().navigate(R.id.action_akunFragment_to_mustLoginBottomSheet)
                binding.toLogin.visibility = View.VISIBLE
                binding.clAkun.visibility = View.GONE
                binding.toLogin.setOnClickListener {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                Toast.makeText(requireActivity(), "You are Unauthorized", Toast.LENGTH_SHORT).show()
                hideLoading()
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

                else -> {}
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun updateUI(userDetail: ProfileResponse) {
        Glide.with(requireActivity())
            .load(userDetail.data.photo)
            .centerCrop()
            .into(binding.ivProfile)
        binding.tvName.text = userDetail.data.name
        binding.tvEmail.text = userDetail.data.email
        binding.tvPhone.text = userDetail.data.phone_number
    }
}
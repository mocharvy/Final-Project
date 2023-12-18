package com.programmer.finalproject.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.programmer.finalproject.databinding.FragmentEditProfileBinding
import com.programmer.finalproject.model.user.update.ProfileResponse
import com.programmer.finalproject.ui.fragment.akun.AkunViewModel
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.profile.EditProfileFragmentViewModel
import com.programmer.finalproject.utils.NetworkResult
import com.programmer.finalproject.utils.reduceFileImage
import com.programmer.finalproject.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint

class EditProfileFragment : Fragment() {
    private lateinit var binding : FragmentEditProfileBinding
    private val akunViewModel: AkunViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val editProfileViewModel: EditProfileFragmentViewModel by viewModels()
    private val REQUEST_IMAGE_SELECT = 100
    private var getFile: File? = null
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
        with(binding) {
            ivPickPhoto.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_SELECT)
            }


            btnSaveProfile.setOnClickListener {
                if (isFieldsNotEmpty()) {
                    uploadImage(getFile)


                } else {
                    handleValidationErrors(etNama, etEmail, etTelepon, etKota, etNegara)
                }

            }
        }

    }

    private fun isFieldsNotEmpty(): Boolean {
        return binding.etNama.text.isNotEmpty() &&
                binding.etEmail.text.isNotEmpty() &&
                binding.etTelepon.text.isNotEmpty() &&
                binding.etKota.text.isNotEmpty() &&
                binding.etNegara.text.isNotEmpty()
    }

    private fun handleValidationErrors(
        name: EditText,
        email: EditText,
        phoneNumber: EditText,
        city: EditText,
        country: EditText
    ) {
        // Handling validation errors and displaying appropriate messages
        with(binding) {
            if (!isNameValid(name)) etNama.error = "Nama harus melebihi 3 huruf dan tidak menggunakan angka atau simbol"
            if (!isEmailValid(email)) etEmail.error = "Email tidak valid! Pastikan email Anda memiliki format yang benar (contoh: DevAcademy@gmail.com)"
            if (isPhoneNumberValid(phoneNumber)) etTelepon.error = "Nomor telepon harus dimulai dengan +62 dan memiliki setidaknya 8 digit"
            if (etNegara.text.isEmpty()) etNegara.error = "Tolong isi, tidak boleh kosong!"
            if (etKota.text.isEmpty()) etKota.error = "Tolong isi, tidak boleh kosong!"
        }
    }

    // Validation functions
    private fun isNameValid(name: EditText): Boolean {
        val namePattern = Regex("^[a-zA-Z ]{3,}\$")
        return name.text.toString().matches(namePattern)
    }

    private fun isEmailValid(email: EditText): Boolean {
        val emailPattern = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")
        return email.text.toString().matches(emailPattern)
    }

    private fun isPhoneNumberValid(phoneNumber: EditText): Boolean {
        val phonePattern = Regex("^\\+62\\d{8,}\$")
        return phoneNumber.text.toString().matches(phonePattern)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImg: Uri = data.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            binding.profileBackground.load(selectedImg)

            getFile = myFile

        }
    }
    private fun uploadImage(photo: File?) {
        val reducedPhoto = photo?.let { reduceFileImage(it) }

        val fileRequestBody = reducedPhoto?.asRequestBody(FILE_TYPE.toMediaTypeOrNull())
        val partFile = fileRequestBody?.let {
            MultipartBody.Part.createFormData(
                FILE_PARAM, reducedPhoto.name, it
            )
        }

        if (partFile != null) {
            authViewModel.token.observe(viewLifecycleOwner) {
                if (it != null) {
                    Log.d("TOKEN", it)
                    val name =
                        binding.etNama.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val email =
                        binding.etEmail.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val phone =
                        binding.etTelepon.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val city =
                        binding.etKota.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    val country =
                        binding.etNegara.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    editProfileViewModel.uploadProfilePic(
                        name,
                        email,
                        phone,
                        city,
                        country,
                        partFile,
                        "Bearer $it"
                    )
                    binding.progressBar.visibility=View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().popBackStack()
                    }, 2000)
                }
            }

        }else{
            Toast.makeText(requireContext(), "Anda harus memilih foto profile\nlagi", Toast.LENGTH_SHORT).show()
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

    private fun updateUI(userDetail: ProfileResponse) {
        binding.apply {
            profileBackground.load(userDetail.data.photo)
            etNama.setText(userDetail.data.name)
            etEmail.setText(userDetail.data.email)
            etTelepon.setText(userDetail.data.phone_number)
            val country = userDetail.data.country.toString()
            val city = userDetail.data.city.toString()
            etNegara.setText(country)
            etKota.setText(city)
        }
    }
    companion object {
        private const val FILE_TYPE = "image/jpeg"
        private const val FILE_PARAM = "photo"
    }
}



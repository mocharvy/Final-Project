package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator
import com.programmer.finalproject.adapter.PagerAdapter
import com.programmer.finalproject.databinding.FilterBottomSheetBinding
import com.programmer.finalproject.databinding.PremiumBottomSheetBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.ui.DetailKelasActivity.Companion.COURSE_ID
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.MateriKelasFragment
import com.programmer.finalproject.ui.fragment.detailkelas.TentangKelasFragment
import com.programmer.finalproject.ui.fragment.kursus.FilterViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PremiumBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : PremiumBottomSheetBinding
    private val detailKelasViewModel: DetailKelasViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PremiumBottomSheetBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestDetailClassFromApi()
    }
    private fun requestDetailClassFromApi() {

        if (COURSE_ID != null) {
            detailKelasViewModel.getDetailCourse(COURSE_ID)
            observeDetailCourse()
        } else {
            Toast.makeText(requireContext(), "Course id is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    //hideLoading()
                    val detailCourse = response.data!!
                    updateUI(detailCourse)
                }

                is NetworkResult.Loading -> {
                    //showLoading()
                }

                is NetworkResult.Error -> {
                    //hideLoading()
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailKelasFragment", "Error: ${response.message}")
                }

                else -> {
                    Log.e("DetailKelasFragment", "Error: ${response.message}")
                }
            }
        }


    }

    private fun updateUI(detailCourse: DetailCourseResponse3) {
        binding.apply {
            tvTitle.text = detailCourse.data?.name
            ivCourseImage.load(detailCourse.data?.category?.image)
        }

    }
}



package com.programmer.finalproject.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.PremiumBottomSheetBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.orders.OrdersViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PremiumBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: PremiumBottomSheetBinding
    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

    private val authViewModel: AuthViewModel by viewModels()
    private val orderViewModel: OrdersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PremiumBottomSheetBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailKelasViewModel.courseId.value = arguments?.getString("courseId")
        requestDetailClassFromApi()

        binding.btnOrderCourse.setOnClickListener {
            orderCourse()
        }
    }

    private fun orderCourse() {
        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                val courseID = detailKelasViewModel.courseId.value
                val orderRequest = courseID?.let { it1 -> OrderRequest(it1) }
                if (orderRequest != null) {
                    orderViewModel.orderCourses("Bearer $it", orderRequest)
                }

                orderViewModel.isError.observe(viewLifecycleOwner) { isError ->
                    if (isError) {
                        Toast.makeText(
                            requireContext(),
                            "Order Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Order has been placed. Please check your order history in Account",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun requestDetailClassFromApi() {
        val courseId = detailKelasViewModel.courseId.value

        if (courseId != null) {
            detailKelasViewModel.getDetailCourse(courseId)
            observeDetailCourse()
        } else {
            Toast.makeText(requireContext(), "Course id is null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val detailCourse = response.data!!
                    updateUI(detailCourse)
                }

                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
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
            tvAuthor.text = detailCourse.data?.facilitator
            tvTime.text = detailCourse.data?.totalDuration.toString()
            tvModule.text = detailCourse.data?.totalChapter.toString()
            tvLevel.text = detailCourse.data?.level
            ivCourseImage.load(detailCourse.data?.category?.image)
            btPrice.text = buildString {
                append(context?.getString(R.string.beli_rp) ?: "Rp. ")
                append(detailCourse.data?.price.toString())

            }
        }
    }
}



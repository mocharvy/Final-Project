package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.programmer.finalproject.databinding.FragmentTentangKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TentangKelasFragment : Fragment() {

    private lateinit var binding : FragmentTentangKelasBinding

    private val detailKelasViewModel : DetailKelasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTentangKelasBinding.inflate(inflater, container, false)

        /*Handler(Looper.getMainLooper()).postDelayed({
            readDetailCourseFromDatabase()
            observeDetailCourse()
            observeReadDetailCourse()
        }, 2000)*/

        hideLoading()

        return binding.root
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    hideLoading()
                    val detailCourse = result.data!!
                    updateUI(detailCourse)
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(),"Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailKelasFragment", "Error: ${result.message}")
                }

                else -> {
                    Log.e("DetailKelasFragment", "Error: ${result.message}")
                }
            }
        }
    }

    private fun observeReadDetailCourse() {
        detailKelasViewModel.readDetailCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                val detailCourse = database.first().detailCourseResponse
                updateUI(detailCourse)
            } else {
                Toast.makeText(requireContext(),"Database is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readDetailCourseFromDatabase() {
        Log.d("Read detail course database", "read detail course database called")
        lifecycleScope.launch {
            detailKelasViewModel.readDetailCourse.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    detailKelasViewModel.readDetailCourse
                } else {
                    requestDetailClassFromApi()
                }
            }
        }
    }


    private fun requestDetailClassFromApi() {
        Log.d("Detail course API", "detail course api called")
        detailKelasViewModel.courseId.observe(viewLifecycleOwner) { courseId ->
            if (courseId.isNotEmpty()) {
                detailKelasViewModel.getDetailCourse(courseId)
            } else {
                Log.d("Course Id", "Invalid courseId")
            }
        }

    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun updateUI(detailCourse: DetailCourseResponse) {
        binding.tvDesc.text = detailCourse.dataDetailCourse.name
        binding.tvDescFor.text = detailCourse.dataDetailCourse.description
    }

}
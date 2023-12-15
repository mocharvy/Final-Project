package com.programmer.finalproject.ui.fragment.kursus

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.AllCourseAdapter
import com.programmer.finalproject.databinding.FragmentKursusBinding
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KursusFragment : Fragment() {

    private lateinit var binding: FragmentKursusBinding
    private lateinit var allCoursesAdapter: AllCourseAdapter

    private val kursusViewModel: KursusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursusBinding.inflate(inflater, container, false)


        allCoursesAdapter = AllCourseAdapter { courseId ->
            val intent = Intent(context, DetailKelasActivity::class.java)
            intent.putExtra("courseId", courseId)
            context?.startActivity(intent)
        }

        setupRecyclerView()
        requestCourseFromApi()

        binding.tvTopikKelas.setOnClickListener {
            val intent = Intent(requireContext(), DetailKelasActivity::class.java)
            startActivity(intent)
        }

        binding.tvFilter.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_filterBottomSheet)
        }

        binding.tvPremium.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_detailPaymentFragment)
        }


        return binding.root
    }

    private fun readCourseFromDatabase() {
        Log.d("Read course database", "read course database called")
        kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                allCoursesAdapter.setData(database.first().listAllCoursesResponse)
                hideShimmerEffect()
            } else {
                requestCourseFromApi()
            }
        }
    }

    private fun requestCourseFromApi() {
        Log.d("Call course API", "api course called")
        kursusViewModel.getListCourse()
        kursusViewModel.listAllCoursesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    Log.d("Call success", "api called successfully")
                    response.data?.let {
                        Log.d("Adapter Debug", "Size before setData: ${allCoursesAdapter.itemCount}")
                        Log.d("DataDetailCourse2 debug", "${response.data}")
                        allCoursesAdapter.setData(it)
                        Log.d("Adapter Debug", "Size after setData: ${allCoursesAdapter.itemCount}")

                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadCourseFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }

                else -> {
                    Toast.makeText(requireActivity(), "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadCourseFromCache() {
        kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                allCoursesAdapter.setData(database.first().listAllCoursesResponse)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = allCoursesAdapter
        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.rvCourse.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvCourse.visibility = View.VISIBLE
    }
}
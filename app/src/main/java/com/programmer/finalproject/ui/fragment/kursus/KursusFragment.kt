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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.AllCourseAdapter
import com.programmer.finalproject.databinding.FragmentKursusBinding
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KursusFragment : Fragment() {

    private lateinit var binding: FragmentKursusBinding
    private lateinit var allCursesAdapter: AllCourseAdapter

    private val kursusViewModel: KursusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursusBinding.inflate(inflater, container, false)


        allCursesAdapter = AllCourseAdapter()

        setupRecyclerView()
        readCourseFromDatabase()

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
        lifecycleScope.launch {
            kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    allCursesAdapter.setData(database.first().listAllCoursesResponse)
                    hideShimmerEffect()
                } else {
                    requestCourseFromApi()
                }
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
                    response.data?.let { allCursesAdapter.setData(it) }
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
            }
        }
    }

    private fun loadCourseFromCache() {
        kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                allCursesAdapter.setData(database.first().listAllCoursesResponse)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = allCursesAdapter
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
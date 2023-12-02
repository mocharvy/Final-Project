package com.programmer.finalproject.ui.fragment.kursus

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentKursusBinding
import com.programmer.finalproject.ui.DetailKelasActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KursusFragment : Fragment() {

    private lateinit var binding: FragmentKursusBinding
    private lateinit var coursesAdapter: CoursesAdapter

    private val kursusViewModel: KursusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursusBinding.inflate(inflater, container, false)


        coursesAdapter = CoursesAdapter()

        setupRecyclerView()

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

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = coursesAdapter
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
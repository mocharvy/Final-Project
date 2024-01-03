package com.programmer.finalproject.ui.fragment.kelas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.TrackerClassAdapter
import com.programmer.finalproject.databinding.FragmentKelasBinding
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasActivity
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.beranda.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KelasFragment : Fragment() {
    private lateinit var binding: FragmentKelasBinding

    private val viewModel: BerandaViewModel by viewModels()
    private val trackerViewModel: KelasViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var trackerAdapter: TrackerClassAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentKelasBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        showTabLayout()
        getTrackerClass("")

        binding.apply {
            etSearch.setOnClickListener {
                findNavController().navigate(R.id.action_kelasFragment_to_searchFragment)
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.text.toString() == "All") {
                        getTrackerClass("")

                    } else {
                        val selectedProgress = tab?.text.toString()
                        getTrackerClass(selectedProgress)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun getTrackerClass(selectedProgress: String) {
        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {

                trackerViewModel.getTrackerClass("Bearer $it", selectedProgress)

                trackerViewModel.getListTrackerClass.observe(viewLifecycleOwner) { list ->
                    trackerAdapter = TrackerClassAdapter { courseId ->
                        navigateToDetailKelas(courseId)
                    }

                    binding.recycleviewClassProses.adapter = trackerAdapter
                    binding.recycleviewClassProses.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    trackerAdapter.submitList(list?.data)

                    if (list?.data.isNullOrEmpty()) {
                        binding.imageView3.visibility = View.VISIBLE
                    } else {
                        binding.imageView3.visibility = View.GONE
                    }

                }
            } else {
                Toast.makeText(requireContext(), "You need to log in first", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun navigateToDetailKelas(courseId: String) {
        val intent = Intent(context, DetailKelasActivity::class.java).apply {
            putExtra("courseId", courseId)
        }
        startActivity(intent)
    }

    private fun showTabLayout() {
        val tabLayout = binding.tabLayout

        tabLayout.addTab(tabLayout.newTab().setText("All"))
        tabLayout.addTab(tabLayout.newTab().setText("In Progress"))
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"))

    }


    private fun getCategories() {
        viewModel.getCategories()

        viewModel.getListCategory.observe(viewLifecycleOwner) { list ->
            categoryAdapter = CategoryAdapter()

            binding.rvCategoryClass.adapter = categoryAdapter
            binding.rvCategoryClass.layoutManager = GridLayoutManager(requireContext(), 2)
            categoryAdapter.submitList(list?.data)

        }
    }

}
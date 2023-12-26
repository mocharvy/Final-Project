package com.programmer.finalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.AllCourseAdapter
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.TrackerClassAdapter
import com.programmer.finalproject.databinding.FragmentKelasBinding
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.ui.bottomsheet.MustLoginBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.beranda.BerandaViewModel
import com.programmer.finalproject.ui.kelas.KelasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KelasFragment : Fragment() {
    private lateinit var binding: FragmentKelasBinding
    private val viewModel: BerandaViewModel by viewModels()
    private val trackerViewModel: KelasViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var trackerAdapter: TrackerClassAdapter
    private  val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentKelasBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        showTabLayout()
        getTrackerClass("")
        initializeTrackerAdapter()


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

    private fun initializeTrackerAdapter() {
        trackerAdapter = TrackerClassAdapter(requireContext()) { courseId ->
            val intent = Intent(requireContext(), DetailKelasActivity::class.java)
            intent.putExtra("courseId", courseId)
            startActivity(intent)
        }
        binding.recycleviewClassProses.adapter = trackerAdapter
        binding.recycleviewClassProses.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun showMustLoginBottomSheet() {
        val bottomSheet = MustLoginBottomSheet()
        bottomSheet.show(parentFragmentManager, "MustLoginBottomSheet")
    }

    private fun getTrackerClass(selectedProgress: String) {
        authViewModel.token.observe(viewLifecycleOwner) { it ->
            it?.let { token ->
                trackerViewModel.getTrackerClass("Bearer $token", selectedProgress)

                trackerViewModel.getListTrackerClass.observe(viewLifecycleOwner) { list ->
                    trackerAdapter.submitList(list?.data)
                }

                binding.recycleviewClassProses.adapter = trackerAdapter
                binding.recycleviewClassProses.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            } ?: run {
                showMustLoginBottomSheet()
            }
        }
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
package com.programmer.finalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.TrackerClassAdapter
import com.programmer.finalproject.databinding.FragmentKelasBinding
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
        showTab()

        binding.apply {
            tvAll.setBackgroundResource(R.drawable.background_hover)
            tvAll.setOnClickListener {
                tvAll.setBackgroundResource(R.drawable.background_hover)
                tvInprogres.setBackgroundResource(R.drawable.default_bg_text)
                tvSelesai.setBackgroundResource(R.drawable.default_bg_text)
            }
            tvInprogres.setOnClickListener {
                tvInprogres.setBackgroundResource(R.drawable.background_hover)
                tvSelesai.setBackgroundResource(R.drawable.default_bg_text)
                tvAll.setBackgroundResource(R.drawable.default_bg_text)

            }
            tvSelesai.setOnClickListener {
                tvSelesai.setBackgroundResource(R.drawable.background_hover)
                tvInprogres.setBackgroundResource(R.drawable.default_bg_text)
                tvAll.setBackgroundResource(R.drawable.default_bg_text)

                etSearch.setOnClickListener {
                    findNavController().navigate(R.id.action_kelasFragment_to_searchFragment)
                }
            }

            authViewModel.token.observe(viewLifecycleOwner){
                if(it != null){

                    trackerViewModel.getTrackerClass("Bearer $it","")

                    trackerViewModel.getListTrackerClass.observe(viewLifecycleOwner) { list ->
                        trackerAdapter = TrackerClassAdapter()

                        binding.recycleviewClassProses.adapter = trackerAdapter
                        binding.recycleviewClassProses.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        trackerAdapter.submitList(list?.data)

                    }
                }
            }
        }

    }
    private fun showTab(){
        val allTab = binding.tabLayout.newTab()
        allTab.text = "All"
        binding.tabLayout.addTab(allTab)

        val premiumTab = binding.tabLayout.newTab()
        premiumTab.text = "Premium"
        binding.tabLayout.addTab(premiumTab)

        val freeTab = binding.tabLayout.newTab()
        freeTab.text = "Free"
        binding.tabLayout.addTab(freeTab)
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
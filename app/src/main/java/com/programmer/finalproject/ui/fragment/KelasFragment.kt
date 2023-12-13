package com.programmer.finalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.databinding.FragmentKelasBinding
import com.programmer.finalproject.ui.fragment.beranda.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KelasFragment : Fragment() {
    private lateinit var binding: FragmentKelasBinding
    private val viewModel: BerandaViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

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

        binding.apply {
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
        }

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
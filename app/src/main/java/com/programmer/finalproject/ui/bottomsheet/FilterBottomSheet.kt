package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.programmer.finalproject.databinding.FilterBottomSheetBinding
import com.programmer.finalproject.ui.fragment.kursus.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FilterBottomSheetBinding
    private val filterViewModel: FilterViewModel by viewModels({ requireActivity() })

    private var recChipText = "Paling Baru"
    private var recChipId = 0
    private var categoryChipText = "Web Development"
    private var categoryChipId = 0
    private var levelChipText = "Beginner"
    private var levelChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)

        binding.recChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            if (selectedChipId.isNotEmpty()) {
                val chip = group.findViewById<Chip>(selectedChipId.first())
                val selectedRecType = chip.text.toString()
                recChipText = selectedRecType
                recChipId = selectedChipId.first()
            }
        }

        binding.categoryChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            if (selectedChipId.isNotEmpty()) {
                val chip = group.findViewById<Chip>(selectedChipId.first())
                val selectedCategoryType = chip.text.toString()
                categoryChipText = selectedCategoryType
                categoryChipId = selectedChipId.first()
            }
        }

        binding.levelChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            if (selectedChipId.isNotEmpty()) {
                val chip = group.findViewById<Chip>(selectedChipId.first())
                val selectedLevelType = chip.text.toString()
                levelChipText = selectedLevelType
                levelChipId = selectedChipId.first()
            }
        }

        binding.btApply.setOnClickListener {
            applyFilters()
        }


        return binding.root
    }

    private fun applyFilters() {
        val recFilter = recChipText
        val categoryFilter = categoryChipText
        val levelFilter = levelChipText
        Log.d("FILTER","$recFilter, $categoryFilter, $levelFilter")

        filterViewModel.setFilterData(recFilter, categoryFilter, levelFilter)

        filterViewModel.filterLiveData.observe(viewLifecycleOwner) {
            Log.d("FILTER DATA BS", "${it.first}, ${it.second}, ${it.third}")
        }

        dismiss()
    }


}
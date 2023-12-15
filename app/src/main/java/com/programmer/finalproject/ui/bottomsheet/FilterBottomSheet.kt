package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.programmer.finalproject.databinding.FilterBottomSheetBinding
import java.util.Locale

class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : FilterBottomSheetBinding

    private var recChipText = "Paling Baru"
    private var recChipId = 0
    private var categoryChipText = "Web Development"
    private var categoryChipId = 0
    private var levelChipText = "Beginner"
    private var levelChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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


        return binding.root
    }



}
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
import com.programmer.finalproject.databinding.PremiumBottomSheetBinding
import com.programmer.finalproject.ui.DetailKelasActivity.Companion.COURSE_ID
import com.programmer.finalproject.ui.fragment.kursus.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PremiumBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : PremiumBottomSheetBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PremiumBottomSheetBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.textView5.text = COURSE_ID
    }





}
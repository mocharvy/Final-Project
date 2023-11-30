package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.databinding.OnboardingBottomSheetBinding

class OnboardingBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: OnboardingBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }
}
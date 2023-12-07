package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.databinding.PurchasedBottomSheetBinding

class PurchasedBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: PurchasedBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchasedBottomSheetBinding.inflate(inflater, container, false)

        binding.btnPurchased.setOnClickListener {
            val onboardingBottomSheet = OnboardingBottomSheet()
            onboardingBottomSheet.show(
                requireActivity().supportFragmentManager,
                onboardingBottomSheet.tag
            )
            dismiss()
        }

        return binding.root
    }
}
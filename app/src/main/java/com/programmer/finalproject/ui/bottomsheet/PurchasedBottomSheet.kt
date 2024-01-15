package com.programmer.finalproject.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.databinding.PurchasedBottomSheetBinding

class PurchasedBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: PurchasedBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PurchasedBottomSheetBinding.inflate(inflater, container, false)

        binding.btnPurchased.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            dismiss()
        }

        return binding.root
    }
}
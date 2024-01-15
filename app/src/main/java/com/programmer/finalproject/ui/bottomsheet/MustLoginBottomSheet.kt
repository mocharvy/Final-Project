package com.programmer.finalproject.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.programmer.finalproject.databinding.MustLoginBottomSheetBinding
import com.programmer.finalproject.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MustLoginBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: MustLoginBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MustLoginBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogin.setOnClickListener {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            close.setOnClickListener{
                dismiss()
            }
        }
    }
}

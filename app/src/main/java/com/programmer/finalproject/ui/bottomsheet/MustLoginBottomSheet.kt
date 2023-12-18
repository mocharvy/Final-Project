package com.programmer.finalproject.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.programmer.finalproject.R
import androidx.navigation.fragment.findNavController
import com.programmer.finalproject.databinding.MustLoginBottomSheetBinding

class MustLoginBottomSheet : DialogFragment() {

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
                dismiss()
                findNavController().navigate(R.id.action_mustLoginBottomSheet_to_loginFragment)
            }
        }
    }
}
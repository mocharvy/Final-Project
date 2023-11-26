package com.programmer.finalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentHistoryPaymentBinding


class HistoryPaymentFragment : Fragment() {
    private lateinit var binding : FragmentHistoryPaymentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryPaymentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}
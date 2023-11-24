package com.programmer.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.programmer.finalproject.databinding.FragmentOtpBinding


class OtpFragment : Fragment() {
    private lateinit var binding : FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}
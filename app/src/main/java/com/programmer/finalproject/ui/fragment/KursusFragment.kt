package com.programmer.finalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentKursusBinding
import com.programmer.finalproject.ui.DetailKelasActivity

class KursusFragment : Fragment() {

    private lateinit var binding: FragmentKursusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursusBinding.inflate(inflater, container, false)


        binding.tvTopikKelas.setOnClickListener {
            val intent = Intent(requireContext(), DetailKelasActivity::class.java)
            startActivity(intent)
        }

        binding.tvFilter.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_filterBottomSheet)
        }

        binding.tvPremium.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_detailPaymentFragment)
        }


        return binding.root
    }
}
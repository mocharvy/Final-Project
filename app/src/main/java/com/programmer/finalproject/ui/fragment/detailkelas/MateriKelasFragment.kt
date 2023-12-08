package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentTentangKelasBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MateriKelasFragment : Fragment() {

    private lateinit var binding: FragmentTentangKelasBinding

    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTentangKelasBinding.inflate(inflater, container, false)







        return binding.root
    }

}
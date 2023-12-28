package com.programmer.finalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentFilterCategoryBinding
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.ui.fragment.beranda.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterCategoryFragment : Fragment() {
    private lateinit var binding: FragmentFilterCategoryBinding
    private val viewModel: BerandaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}



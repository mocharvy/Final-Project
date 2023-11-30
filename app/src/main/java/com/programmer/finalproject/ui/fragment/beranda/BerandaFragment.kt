package com.programmer.finalproject.ui.fragment.beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentBerandaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BerandaFragment : Fragment() {
    private lateinit var binding : FragmentBerandaBinding
    private val viewModel: BerandaViewModel by viewModels()
    private lateinit var listCoursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBerandaBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCourse()

        binding.apply {

        }
    }

    private fun getCourse() {
        viewModel.getCourses()

        viewModel.getListCourses.observe(viewLifecycleOwner) { list ->
            listCoursesAdapter = CoursesAdapter()

            binding.rvCourses.adapter = listCoursesAdapter
            binding.rvCourses.layoutManager = LinearLayoutManager(requireContext())

            listCoursesAdapter.submitList(list?.data)

        }
    }

}
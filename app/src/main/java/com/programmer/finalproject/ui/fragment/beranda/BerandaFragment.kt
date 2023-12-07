package com.programmer.finalproject.ui.fragment.beranda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentBerandaBinding
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BerandaFragment : Fragment() {
    private lateinit var binding : FragmentBerandaBinding
    private val viewModel: BerandaViewModel by viewModels()
    private lateinit var listCoursesAdapter: CoursesAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private val authViewModel: AuthViewModel by viewModels()

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
        getCategories()

        binding.apply {

        }

        authViewModel.token.observe(viewLifecycleOwner) {
            it?.let { token ->
                Log.d("Access Token through token", token)
                Toast.makeText(requireActivity(), "Token = $token", Toast.LENGTH_SHORT).show()
            }

        }

        /*authViewModel.accountData.observe(viewLifecycleOwner) {
            Log.d("Access Token accountData", it?.data?.accessToken.toString())
            Toast.makeText(requireActivity(), "Acces Token = ${it?.data?.accessToken}", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun getCourse() {
        viewModel.getCourses()

        viewModel.getListCourses.observe(viewLifecycleOwner) { list ->
            listCoursesAdapter = CoursesAdapter()

            binding.rvCourses.adapter = listCoursesAdapter
            binding.rvCourses.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            listCoursesAdapter.submitList(list?.data)

        }
    }

    private fun getCategories() {
        viewModel.getCategories()

        viewModel.getListCategory.observe(viewLifecycleOwner) { list ->
            categoryAdapter = CategoryAdapter()

            binding.rvCategory.adapter = categoryAdapter
            binding.rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
            categoryAdapter.submitList(list?.data)

        }
    }

}
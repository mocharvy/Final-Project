package com.programmer.finalproject.ui.fragment.beranda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentBerandaBinding
import com.programmer.finalproject.model.courses.Category
import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.ui.DetailKelasActivity
import com.programmer.finalproject.ui.bottomsheet.MustLoginBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BerandaFragment : Fragment() {
    private lateinit var binding : FragmentBerandaBinding
    private val viewModel: BerandaViewModel by viewModels()
    private lateinit var listCoursesAdapter: CoursesAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var prefManager: PreferenceManager

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBerandaBinding.inflate(layoutInflater,container,false)
        prefManager = PreferenceManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        checkLoginStatus()

        listCoursesAdapter = CoursesAdapter { course ->
            if(authViewModel.token.value==null && course.type == "Premium"){
                findNavController().navigate(R.id.action_berandaFragment_to_mustLoginBottomSheet)

            }else{
                val intent = Intent(requireContext(), DetailKelasActivity::class.java)
                intent.putExtra("courseId", course.id)
                startActivity(intent)
            }

        }

        binding.rvCourses.adapter = listCoursesAdapter
        binding.rvCourses.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab?.text.toString()== "All"){
                    getCourse("")

                }else{
                    val selectedCategory = tab?.text.toString()
                    getCourse(selectedCategory)
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        val allTab = binding.tabLayout.newTab()
        allTab.text = "All"
        binding.tabLayout.addTab(allTab)

        // Set the default category to "All" when the fragment is created
        getCourse("")

        getCategories()

        binding.tvLihatsemua.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_detailCategoryFragment2)
        }


        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_searchFragment)
        }

        authViewModel.token.observe(viewLifecycleOwner) {
            it?.let { token ->
                Log.d("Access Token through token", token)
                Toast.makeText(requireActivity(), "Token = $token", Toast.LENGTH_SHORT).show()
            }
            /*authViewModel.isLogin.observe(viewLifecycleOwner){
                Toast.makeText(requireActivity(), "Is Login = $it", Toast.LENGTH_SHORT).show()

            }*/

        }
    }



    private fun getCourse(categoryFilter: String) {
        viewModel.getCourses(categoryFilter)

        viewModel.getListCourses.observe(viewLifecycleOwner) { list ->
            listCoursesAdapter.submitList(list?.data)

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


            showTabCategory(list?.data)

            binding.rvCategory.adapter = categoryAdapter
            binding.rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
            categoryAdapter.submitList(list?.data)

        }
    }

    private fun showTabCategory(data: List<Category>?) {
        val tabCategory = binding.tabLayout
        data?.forEach { category ->
            val tab= tabCategory.newTab()
            tab.text = category.category
            tabCategory.addTab(tab)
        }
    }
}
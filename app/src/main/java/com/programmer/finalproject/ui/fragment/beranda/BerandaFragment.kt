package com.programmer.finalproject.ui.fragment.beranda

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.CategoryAdapter
import com.programmer.finalproject.adapter.CoursesAdapter
import com.programmer.finalproject.databinding.FragmentBerandaBinding
import com.programmer.finalproject.model.courses.Category
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasActivity
import com.programmer.finalproject.ui.bottomsheet.PremiumBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding

    private lateinit var listCoursesAdapter: CoursesAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private val authViewModel: AuthViewModel by viewModels()
    private val viewModel: BerandaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBerandaBinding.inflate(layoutInflater, container, false)

        onBackPressed()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCoursesAdapter = CoursesAdapter { course ->
            authViewModel.token.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (course.type == "Free") {
                        val intent = Intent(requireContext(), DetailKelasActivity::class.java)
                        intent.putExtra("courseId", course.id)
                        startActivity(intent)
                    } else {
                        showPaymentConfirmationDialog(course.id)
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Anda harus masuk terlebih dahulu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                if (tab?.text.toString() == "All") {
                    getCourse("")

                } else {
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
        getCourse("")

        getCategories()

        binding.tvLihatsemua.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_detailCategoryFragment2)
        }


        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_searchFragment)
        }
    }

    private fun showPaymentConfirmationDialog(courseId: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_confirmation_order)

        val btnCancel = dialog.findViewById<MaterialButton>(R.id.btn_batal)
        val btnBuy = dialog.findViewById<MaterialButton>(R.id.btn_beli_kelas)

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnBuy.setOnClickListener {
            val bundle = Bundle().apply {
                putString("courseId", courseId)
            }

            val premiumBottomSheet = PremiumBottomSheet()
            premiumBottomSheet.arguments = bundle

            premiumBottomSheet.show(
                requireActivity().supportFragmentManager,
                premiumBottomSheet.tag
            )

            dialog.dismiss()
        }

        dialog.show()
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
            val tab = tabCategory.newTab()
            tab.text = category.category
            tabCategory.addTab(tab)
        }
    }

    private fun onBackPressed() {
        val navController = findNavController()
        requireActivity()
            .onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                if (navController.currentDestination?.id == R.id.berandaFragment) {
                    requireActivity().finish()
                } else {
                    navController.navigateUp()
                }
            }
    }
}
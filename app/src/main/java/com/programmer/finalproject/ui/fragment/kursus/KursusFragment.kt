package com.programmer.finalproject.ui.fragment.kursus

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.AllCourseAdapter
import com.programmer.finalproject.databinding.FragmentKursusBinding
import com.programmer.finalproject.ui.bottomsheet.PremiumBottomSheet
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KursusFragment : Fragment() {

    private lateinit var binding: FragmentKursusBinding
    private lateinit var allCoursesAdapter: AllCourseAdapter

    private val loginViewModel: LoginViewModel by viewModels()
    private val kursusViewModel: KursusViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels({ requireActivity() })

    private var typeChipText = "Free"
    private var typeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKursusBinding.inflate(inflater, container, false)


        allCoursesAdapter = AllCourseAdapter { courseId ->
            val clickedCourse = allCoursesAdapter.course.firstOrNull { it.id == courseId }
            clickedCourse?.let { _ ->
                loginViewModel.token.observe(viewLifecycleOwner) {
                    if (it != null) {
                        showPaymentConfirmationDialog(courseId)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Anda harus masuk terlebih dahulu.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

        setupRecyclerView()
        requestCourseFromApi()

        binding.tvFilter.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_filterBottomSheet)
        }

        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_kursusFragment_to_searchFragment)
        }

        filterViewModel.filterLiveData.observe(viewLifecycleOwner) { filterData ->
            Log.d("FILTER DATA", "${filterData.first}, ${filterData.second}, ${filterData.third}")
            requestCourseFromApiByFilter(
                filterData.first,
                filterData.second,
                filterData.third,
                null
            )
        }

        binding.recChipGroup.setOnCheckedStateChangeListener { group, selectedChipId ->
            if (selectedChipId.isNotEmpty()) {
                val chip = group.findViewById<Chip>(selectedChipId.first())
                when (chip.text.toString()) {
                    "Kelas premium" -> {
                        typeChipText = "Premium"
                        typeChipId = selectedChipId.first()
                        requestCourseFromApiByType(typeChipText)
                    }

                    "Kelas gratis" -> {
                        typeChipText = "Free"
                        typeChipId = selectedChipId.first()
                        requestCourseFromApiByType(typeChipText)
                    }

                    "Semua kelas" -> {
                        requestCourseFromApi()
                    }
                }
            }
        }

        return binding.root
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

    private fun readCourseFromDatabase() {
        Log.d("Read course database", "read course database called")
        kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                allCoursesAdapter.setData(database.first().listAllCoursesResponse)
                hideShimmerEffect()
            } else {
                requestCourseFromApi()
            }
        }
    }

    private fun requestCourseFromApi() {
        kursusViewModel.getListCourse(null, null, null, null)
        kursusViewModel.listAllCoursesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()

                    response.data?.let {
                        allCoursesAdapter.setData(it)

                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadCourseFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }

                else -> {
                    Toast.makeText(requireActivity(), "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestCourseFromApiByFilter(
        filter: String?,
        category: String?,
        level: String?,
        type: String?
    ) {
        kursusViewModel.getListCourse(filter, category, level, type)
        kursusViewModel.listAllCoursesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()

                    response.data?.let {
                        allCoursesAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadCourseFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }

                else -> {
                    Toast.makeText(requireActivity(), "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestCourseFromApiByType(type: String?) {
        kursusViewModel.getListCourse(null, null, null, type)
        kursusViewModel.listAllCoursesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()

                    response.data?.let {
                        allCoursesAdapter.setData(it)
                    }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadCourseFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }

                else -> {
                    Toast.makeText(requireActivity(), "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadCourseFromCache() {
        kursusViewModel.readCourse.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                allCoursesAdapter.setData(database.first().listAllCoursesResponse)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = allCoursesAdapter
        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.rvCourse.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvCourse.visibility = View.VISIBLE
    }
}
package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.finalproject.adapter.ChapterAdapter
import com.programmer.finalproject.databinding.FragmentMateriKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MateriKelasFragment : Fragment() {

    private lateinit var binding: FragmentMateriKelasBinding

    private val detailKelasViewModel: DetailKelasViewModel by viewModels()
    private val materiKelasViewModel: MateriKelasViewModel by viewModels()

    private var detailCourse: DetailCourseResponse3? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMateriKelasBinding.inflate(inflater, container, false)

        @Suppress("DEPRECATION")
        detailCourse = arguments?.getParcelable(ARG_DETAIL_COURSE)
        Log.d("MateriKelasFragment", "Detail Course: $detailCourse")

        val chapterAdapter = ChapterAdapter(requireContext(), detailCourse?.data?.chapters) { chapterId ->
            materiKelasViewModel.getDetailChapter(chapterId)
        }
        binding.rvChapter.adapter = chapterAdapter
        binding.rvChapter.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        materiKelasViewModel.detailChapterResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val newModules = response.data?.data?.modules
                    chapterAdapter.updateModules(newModules)
                }
                else -> {
                    Toast.makeText(requireContext(),"Data already loaded", Toast.LENGTH_SHORT).show()
                }
            }
        }



        return binding.root
    }

    companion object {
        private const val ARG_DETAIL_COURSE = "arg_detail_course"
        fun newInstance(detailCourse: DetailCourseResponse3): MateriKelasFragment {
            val fragment = MateriKelasFragment()
            val args = Bundle()
            args.putParcelable(ARG_DETAIL_COURSE, detailCourse)
            fragment.arguments = args
            return fragment
        }
    }

}
package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.programmer.finalproject.databinding.FragmentMateriKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MateriKelasFragment : Fragment() {

    private lateinit var binding: FragmentMateriKelasBinding

    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMateriKelasBinding.inflate(inflater, container, false)







        return binding.root
    }

    companion object {
        private const val ARG_DETAIL_COURSE = "arg_detail_course"
        fun newInstance(detailCourse: DetailCourseResponse3): TentangKelasFragment {
            val fragment = TentangKelasFragment()
            val args = Bundle()
            args.putParcelable(ARG_DETAIL_COURSE, detailCourse)
            fragment.arguments = args
            return fragment
        }
    }

}
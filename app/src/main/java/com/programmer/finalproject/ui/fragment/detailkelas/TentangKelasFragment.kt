package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.programmer.finalproject.databinding.FragmentTentangKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TentangKelasFragment : Fragment() {

    private lateinit var binding : FragmentTentangKelasBinding

    private val detailKelasViewModel : DetailKelasViewModel by viewModels()

    private var detailCourse: DetailCourseResponse3? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTentangKelasBinding.inflate(inflater, container, false)

        @Suppress("DEPRECATION")
        detailCourse = arguments?.getParcelable(ARG_DETAIL_COURSE)

        updateUI()


        return binding.root
    }

    private fun updateUI() {
        detailCourse?.let {
            binding.tvDesc.text = it.data?.onBoarding
            binding.tvDescFor.text = it.data?.description
        }
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
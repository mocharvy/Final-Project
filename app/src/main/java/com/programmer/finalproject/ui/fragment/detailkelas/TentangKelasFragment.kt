package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.programmer.finalproject.databinding.FragmentTentangKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        detailCourse = arguments?.getParcelable("detailCourse")

        Handler(Looper.getMainLooper()).postDelayed({
            updateUI()
        }, 2000)

        return binding.root
    }

    private fun updateUI() {

        Log.e("DATA DETAIL", "$detailCourse")
        binding.tvDesc.text = detailCourse?.data?.onBoarding
        binding.tvDescFor.text = detailCourse?.data?.description
    }

}
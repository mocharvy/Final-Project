package com.programmer.finalproject.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.programmer.finalproject.adapter.PagerAdapter
import com.programmer.finalproject.databinding.ActivityDetailKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.MateriKelasFragment
import com.programmer.finalproject.ui.fragment.detailkelas.TentangKelasFragment
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailKelasActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailKelasBinding

    private val detailKelasViewModel : DetailKelasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailKelasViewModel.courseId.value = intent.getStringExtra("courseId") ?: ""

        requestDetailClassFromApi()
        observeDetailCourse()

        val fragments = ArrayList<Fragment>()
        fragments.add(TentangKelasFragment())
        fragments.add(MateriKelasFragment())

        val titles = ArrayList<String>()
        titles.add("Tentang Kelas")
        titles.add("Materi Kelas")

        val pagerAdapter = PagerAdapter(
            fragments,
            this
        )

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tab, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun requestDetailClassFromApi() {
        Log.d("Detail course API", "detail course api called")
        /*detailKelasViewModel.courseId.observe(this) { courseId ->
            if (courseId.isNotEmpty()) {
                detailKelasViewModel.getDetailCourse(courseId)
            } else {
                Log.d("Course Id", "Invalid courseId")
            }
        }*/

        val courseId = detailKelasViewModel.courseId.value
        Log.d("COURSE ID1", courseId.toString())
        if (courseId != null) {
            Log.d("COURSE ID2", courseId.toString())
            detailKelasViewModel.getDetailCourse(courseId)
        } else {
            Log.d("Course Id", "Invalid courseId")
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    //hideLoading()
                    val detailCourse = result.data!!
                    updateUI(detailCourse)
                }

                is NetworkResult.Loading -> {
                    //showLoading()
                }

                is NetworkResult.Error -> {
                    //hideLoading()
                    Toast.makeText(this,"Error occurred", Toast.LENGTH_SHORT).show()
                    Log.e("DetailKelasFragment", "Error: ${result.message}")
                }

                else -> {
                    Log.e("DetailKelasFragment", "Error: ${result.message}")
                }
            }
        }
    }

    private fun observeReadDetailCourse() {
        detailKelasViewModel.readDetailCourse.observe(this) { database ->
            if (database.isNotEmpty()) {
                val detailCourse = database.first().detailCourseResponse
                updateUI(detailCourse)
            } else {
                Toast.makeText(this,"Database is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(detailCourse: DetailCourseResponse) {
        binding.tvDesc.text = detailCourse.dataDetailCourse.name
        binding.tvTitle.text = detailCourse.dataDetailCourse.name
        binding.tvAuthor.text = detailCourse.dataDetailCourse.facilitator
        binding.tvLevel.text = detailCourse.dataDetailCourse.level
        binding.tvModule.text = detailCourse.dataDetailCourse.chapters.toString()
    }
}
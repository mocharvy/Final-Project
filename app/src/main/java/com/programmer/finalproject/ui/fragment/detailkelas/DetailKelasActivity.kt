package com.programmer.finalproject.ui.fragment.detailkelas

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.programmer.finalproject.adapter.PagerAdapter
import com.programmer.finalproject.databinding.ActivityDetailKelasBinding
import com.programmer.finalproject.model.detailcourse.DetailCourseResponse3
import com.programmer.finalproject.model.tracker.TrackerRequest
import com.programmer.finalproject.ui.coursetracker.CourseTrackerViewModel
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class DetailKelasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKelasBinding

    private val detailKelasViewModel: DetailKelasViewModel by viewModels()
    private val trackerViewModel: CourseTrackerViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var youtubePlayerView: YouTubePlayerView
    private var videoUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailKelasViewModel.courseId.value = intent.getStringExtra("courseId") ?: ""

        requestDetailClassFromApi()
        //postTracker()

    }

    private fun requestDetailClassFromApi() {
        val courseId = detailKelasViewModel.courseId.value

        if (courseId != null) {
            detailKelasViewModel.getDetailCourse(courseId)
            observeDetailCourse()
        } else {
            Toast.makeText(this, "Course id kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postTracker() {
        val courseId = detailKelasViewModel.courseId.value

        loginViewModel.token.observe(this) {token ->
            if (token != null) {
                if (courseId != null) {
                    val trackerRequest = TrackerRequest(
                        course_id = courseId
                    )
                    trackerViewModel.postTracker("Bearer $token", trackerRequest)
                    Log.d("POST", "$token")
                    Log.d("REQUEST", "$trackerRequest")
                    observePostTracker()
                }
            }
        }

    }

    private fun observePostTracker() {
        trackerViewModel.postTrackerResult.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Toast.makeText(this,"Tracker berhasil dikirm", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Toast.makeText(this,"Gagal mengirim tracker", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeDetailCourse() {
        detailKelasViewModel.detailCourseResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val detailCourse = response.data!!
                    videoUrl = detailCourse.data?.introductionVideo.toString()
                    updateUI(detailCourse)

                    val tentangKelasFragment = TentangKelasFragment.newInstance(detailCourse)
                    val materiKelasFragment = MateriKelasFragment.newInstance(detailCourse)

                    val fragments = ArrayList<Fragment>()
                    fragments.add(tentangKelasFragment)
                    fragments.add(materiKelasFragment)

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

                    if (videoUrl.isNotEmpty()) {
                        val videoId = extractVideoIdFromUrl(videoUrl)
                        youtubePlayerView = binding.youtubePlayerView
                        lifecycle.addObserver(youtubePlayerView)

                        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(videoId, 0F)
                            }
                        })
                    }
                }

                is NetworkResult.Loading -> {
                }

                is NetworkResult.Error -> {
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Database is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(detailCourse: DetailCourseResponse3) {
        binding.tvDesc.text = detailCourse.data?.name
        binding.tvTitle.text = detailCourse.data?.name
        binding.tvAuthor.text = detailCourse.data?.facilitator
        binding.tvLevel.text = detailCourse.data?.level
        binding.tvModule.text = detailCourse.data?.totalChapter.toString()
    }

    private fun extractVideoIdFromUrl(url: String): String {
        val pattern =
            "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher =
            compiledPattern.matcher(url) //url is youtube url for which you want to extract the video id.
        if (matcher.find()) {
            return matcher.group()
        }
        throw IllegalArgumentException("Could not extract video ID from URL: $url")
    }

    override fun onDestroy() {
        super.onDestroy()

        youtubePlayerView.release()
    }

}
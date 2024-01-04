package com.programmer.finalproject.ui.fragment.videomateri

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.programmer.finalproject.databinding.ActivityVideoBinding
import com.programmer.finalproject.model.tracker.PutTrackerRequest
import com.programmer.finalproject.ui.coursetracker.CourseTrackerViewModel
import com.programmer.finalproject.ui.fragment.detailkelas.DetailKelasViewModel
import com.programmer.finalproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoBinding
    private lateinit var youtubePlayerView: YouTubePlayerView

    private val trackerViewModel: CourseTrackerViewModel by viewModels()
    private val detailKelasViewModel: DetailKelasViewModel by viewModels()

    private var lastModule = "1"
    private var moduleID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUrl = intent.getStringExtra("videoUrl")

        val videoIds = videoUrl?.let { extractVideoIdFromUrl(it) }

        youtubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoIds != null) {
                    youTubePlayer.loadVideo(videoIds, 0F)
                }
            }
        })

        //putTrackerById()

    }

    private fun extractVideoIdFromUrl(url: String): String {
        val pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)
        if (matcher.find()) {
            return matcher.group()
        }
        throw IllegalArgumentException("Could not extract video ID from URL: $url")
    }

    private fun putTrackerById() {
        detailKelasViewModel.courseId.observe(this) {courseId ->
            val putTrackerRequest = PutTrackerRequest(
                last_opened_chapter = 2,
                last_opened_module = lastModule.toInt(),
                module_id = moduleID
            )
            trackerViewModel.putTrackerById(courseId, putTrackerRequest)
            Log.d("PUT TRACKER", "$courseId, $putTrackerRequest")
            observePutTrackerById()
        }

    }

    private fun observePutTrackerById() {
        trackerViewModel.putTrackerResult.observe(this) { response ->
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
}
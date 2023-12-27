package com.programmer.finalproject.ui.fragment.videomateri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.programmer.finalproject.databinding.ActivityVideoBinding
import java.util.regex.Pattern

class VideoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityVideoBinding

    private lateinit var youtubePlayerView: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoUrl = intent.getStringExtra("videoUrl")
        Log.d("VIDEO URL", "$videoUrl")

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
}
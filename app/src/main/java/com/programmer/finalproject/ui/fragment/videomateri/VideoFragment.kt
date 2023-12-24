package com.programmer.finalproject.ui.fragment.videomateri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.programmer.finalproject.R
import com.programmer.finalproject.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding

    private lateinit var youtubePlayerView: YouTubePlayerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(layoutInflater, container, false)

        youtubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "xvFZjo5PgG0"
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        youtubePlayerView.release()
    }

}
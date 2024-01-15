package com.programmer.finalproject.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.programmer.finalproject.MainActivity
import com.programmer.finalproject.databinding.ActivitySplashBinding
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserLogin()
        }, delayTime)

    }

    private fun checkUserLogin() {
        loginViewModel.isLogin.observe(this) { isUserLoggedIn ->
            if (isUserLoggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, WalkThroughActivity::class.java))
            }
            finish()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.appImage, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(500)
        val create = ObjectAnimator.ofFloat(binding.tvNow, View.ALPHA, 1f).setDuration(500)
        val free = ObjectAnimator.ofFloat(binding.tvCreate, View.ALPHA, 1f).setDuration(500)
        val wait = ObjectAnimator.ofFloat(binding.tvPlease, View.ALPHA, 1f).setDuration(350)

        AnimatorSet().apply {
            playSequentially(title, create, free, wait)
            start()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        const val delayTime: Long = 4000
    }
}
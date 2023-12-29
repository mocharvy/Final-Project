package com.programmer.finalproject.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.programmer.finalproject.R
import com.programmer.finalproject.adapter.WalkthroughAdapter
import com.programmer.finalproject.databinding.ActivityWalkThruoghBinding

class WalkThroughActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkThruoghBinding

    private lateinit var walkAdapter: WalkthroughAdapter
    private val dots = arrayOfNulls<TextView>(3)
    var currentpage: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkThruoghBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walkAdapter = WalkthroughAdapter(this)
        val viewPager = binding.vpWalkthrough
        viewPager.adapter = walkAdapter

        initAction()

        skipToRegister()
        moveToLogin()

    }

    private fun skipToRegister() {
        binding.button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun moveToLogin() {
        binding.txtMasuk.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initAction() {
        val viewPager = binding.vpWalkthrough
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                dotIndicator(position)
                currentpage = position
            }

        })
    }

    @Suppress("DEPRECATION")
    private fun dotIndicator(position: Int) {
        val dotsLayout = binding.dotsLayout

        dotsLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]?.text = Html.fromHtml("&#8226;")
            dots[i]?.textSize = 35F
            dots[i]?.setTextColor(resources.getColor(R.color.light_grey))

            dotsLayout.addView(dots[i])
        }

        if (dots.isNotEmpty()) {
            dots[position]?.setTextColor(resources.getColor(R.color.dark_blue))
        }
    }
}
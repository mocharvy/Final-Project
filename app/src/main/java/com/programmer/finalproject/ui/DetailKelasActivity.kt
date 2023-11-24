package com.programmer.finalproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.programmer.finalproject.adapter.PagerAdapter
import com.programmer.finalproject.databinding.ActivityDetailKelasBinding
import com.programmer.finalproject.ui.fragment.MateriKelasFragment
import com.programmer.finalproject.ui.fragment.TentangKelasFragment

class DetailKelasActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailKelasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}
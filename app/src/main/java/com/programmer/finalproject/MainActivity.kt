package com.programmer.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.programmer.finalproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()

    }

    private fun setupBottomNav() {
        binding.apply {
            navController = findNavController(R.id.nav_host)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.kelasFragment || destination.id == R.id.berandaFragment || destination.id == R.id.notifikasiFragment||destination.id == R.id.akunFragment||destination.id == R.id.kursusFragment) {
                    binding.navBottom.setupWithNavController(navController)
                    binding.navBottom.visibility = View.VISIBLE

                } else {
                    binding.navBottom.visibility = View.GONE
                }
            }
        }
    }
}
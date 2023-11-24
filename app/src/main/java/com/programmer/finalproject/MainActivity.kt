package com.programmer.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.programmer.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.homeFragment ||
                destination.id == R.id.notifFragment ||
                destination.id == R.id.kelasFragment ||
                destination.id == R.id.kursusFragment ||
                destination.id == R.id.profileFragment
                ) {
                binding.navBottom.setupWithNavController(navController)
                binding.navBottom.visibility = View.VISIBLE

            } else {
                binding.navBottom.visibility = View.GONE
            }
        }
    }
}
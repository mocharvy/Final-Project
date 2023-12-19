package com.programmer.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.programmer.finalproject.databinding.ActivityMainBinding
import com.programmer.finalproject.ui.bottomsheet.MustLoginBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        checkLogin()

    }

    private fun checkLogin() {
        authViewModel.token.observe(this) { token ->
            if (token == null) {
                navController = findNavController(R.id.nav_host)

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.notifikasiFragment, R.id.akunFragment, R.id.kelasFragment -> {
                            val bottomSheetFragmentMustLogin = MustLoginBottomSheet()
                            bottomSheetFragmentMustLogin.show(supportFragmentManager, bottomSheetFragmentMustLogin.tag)

                        }
                    }

                }
            }
        }
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
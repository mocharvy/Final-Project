package com.programmer.finalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.programmer.finalproject.databinding.ActivityMainBinding
import com.programmer.finalproject.ui.bottomsheet.MustLoginBottomSheet
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import com.programmer.finalproject.ui.fragment.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        checkLogin()

    }

    private fun checkLogin() {
        loginViewModel.token.observe(this) {
            Log.d("TOKEN", "$it")
            if (it == null) {
                navController = findNavController(R.id.nav_host)

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.notifikasiFragment, R.id.akunFragment, R.id.kelasFragment -> {
                            val bottomSheetFragmentMustLogin = MustLoginBottomSheet()
                            bottomSheetFragmentMustLogin.show(
                                supportFragmentManager,
                                bottomSheetFragmentMustLogin.tag
                            )
                        }
                    }
                }
            }
        }
    }


    private fun setupBottomNav() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        val navController = navHostFragment.navController
        binding.navBottom.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.berandaFragment, R.id.notifikasiFragment, R.id.kelasFragment, R.id.kursusFragment, R.id.akunFragment -> {
                    binding.navBottom.visibility = View.VISIBLE
                }

                else -> {
                    binding.navBottom.visibility = View.GONE
                }
            }
        }
    }
}
package com.programmer.finalproject

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.programmer.finalproject.ui.fragment.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        authViewModel.token.observe(viewLifecycleOwner) {
            if (it != null) {
                Handler().postDelayed({
                    findNavController().navigate(R.id.action_splashScreenFragment_to_berandaFragment)

                },3000)
                Log.d("TOKEN", it)
            } else {
                Handler().postDelayed({
                    findNavController().navigate(R.id.action_splashScreenFragment_to_walkThroughFragment)
                }, 3000)


            }
        }


    }
}

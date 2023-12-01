package com.programmer.finalproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class SplashScreenFragment : Fragment() {

    private lateinit var txt_devacademy: TextView
    private lateinit var txtPengalaman: TextView
    private lateinit var iv_logo_devacademy: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        // Mengambil referensi dari elemen-elemen tampilan pada layout
        txt_devacademy = view.findViewById(R.id.txt_devacademy)
        txtPengalaman = view.findViewById(R.id.txt_pengalaman)
        iv_logo_devacademy = view.findViewById(R.id.iv_logo_devacademy)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handler untuk menunda navigasi ke MainActivity selama 3000 milidetik (3 detik)
        Handler().postDelayed({
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Opsional, untuk menutup activity saat ini setelah navigasi
        }, 3000)

        // Ubah teks dan gambar pada layout sesuai kebutuhan
        txt_devacademy.text = "DEV-ACADEMY"
        txtPengalaman.text = "Belajar dari Pengalaman Terbaik"
        iv_logo_devacademy.setImageResource(R.drawable.logo_devacademy)
    }
}

package com.programmer.finalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.programmer.finalproject.R
import com.programmer.finalproject.ui.settings.SettingsFragment

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
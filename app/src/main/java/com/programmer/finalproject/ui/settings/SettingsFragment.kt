package com.programmer.finalproject.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.programmer.finalproject.R
import com.programmer.finalproject.ui.notification.DailyReminder
import com.programmer.finalproject.utils.NightMode

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val turnOnOfDarkMode: ListPreference? = findPreference(getString(R.string.pref_key_dark))
        turnOnOfDarkMode?.setOnPreferenceChangeListener { _, newValue ->
            val stringValue = newValue.toString()
            if (stringValue == getString(R.string.pref_dark_auto)) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) updateTheme(
                    NightMode.AUTO.value
                )
                else updateTheme(NightMode.OFF.value)
            } else if (stringValue == getString(R.string.pref_dark_off)) updateTheme(
                NightMode.OFF.value)
            else updateTheme(NightMode.ON.value)

            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val switchNotification: SwitchPreference? =
            findPreference(getString(R.string.pref_key_notify))
        switchNotification?.setOnPreferenceChangeListener { _, newValue ->
            val broadcast = DailyReminder()
            if (newValue == true) {
                broadcast.setDailyReminder(requireContext())
                Toast.makeText(activity, "Notifikasi di aktifkan", Toast.LENGTH_SHORT).show()
                broadcast.showNotification(requireContext())
            } else {
                broadcast.cancelAlarm(requireContext())
                Toast.makeText(activity, "Notifikasi di non-aktifkan", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}
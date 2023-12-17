package com.programmer.finalproject.utils
import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val pref: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return pref.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        pref.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }
}

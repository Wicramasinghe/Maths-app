package com.example.kidsmathgame

import android.content.Context

class SharedPrefManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("theme_pref", Context.MODE_PRIVATE)

    fun setDarkMode(isDark: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkTheme", isDark).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean("isDarkTheme", false)
    }
}

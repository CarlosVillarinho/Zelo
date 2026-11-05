package br.com.carlosvillarinho.zelo.model

import android.content.Context

class Onboarding(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun hasSeenOnboarding(): Boolean = prefs.getBoolean(KEY_SEEN, false)

    fun setOnboardingSeen() {
        prefs.edit().putBoolean(KEY_SEEN, true).apply()
    }

    private companion object {
        const val PREFS_NAME = "zelo_prefs"
        const val KEY_SEEN = "onboarding_seen"
    }
}
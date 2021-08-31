package com.fariq.githubuser

import android.content.Context

internal class ReminderPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "reminder_pref"
        private const val REMINDER_VAL = "reminder"
    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setReminderValue(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(REMINDER_VAL, value)
        editor.apply()
    }
    fun getReminderValue(): Boolean {
        val isTrue = preferences.getBoolean(REMINDER_VAL, false)
        return isTrue
    }
}
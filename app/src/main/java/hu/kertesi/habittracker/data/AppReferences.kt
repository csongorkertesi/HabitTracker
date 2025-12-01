package hu.kertesi.habittracker.data

import android.content.Context

object AppReferences {
    private const val PREF_NAME = "ht_prefs"
    private const val KEY_EMAIL = "email"
    private const val KEY_REMEMBER_ME = "remember_me"

    private const val KEY_HABIT_NAME = "habit_name"
    private const val KEY_HABIT_GOAL = "habit_goal"
    private const val KEY_HABIT_COMPLETED = "habit_completed"

    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_FONT_SCALE = "font_scale"

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun saveLogin(context: Context, email: String, rememberMe: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_EMAIL, if (rememberMe) email else "")
            putBoolean(KEY_REMEMBER_ME, rememberMe)
        }.apply()
    }

    fun loadLogin(context: Context): Pair<String, Boolean> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val email = prefs.getString(KEY_EMAIL, "") ?: ""
        val rememberMe = prefs.getBoolean(KEY_REMEMBER_ME, false)
        return Pair(email, rememberMe)
    }


    fun saveSettings(context: Context, darkMode: Boolean, fontScale: Float) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_DARK_MODE, darkMode)
            putFloat(KEY_FONT_SCALE, fontScale)
        }.apply()
    }

    fun loadSettings(context: Context): Pair<Boolean, Float> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val darkMode = prefs.getBoolean(KEY_DARK_MODE, false)
        val fontScale = prefs.getFloat(KEY_FONT_SCALE, 1f)
        return Pair(darkMode, fontScale)
    }

    fun saveHabit(context: Context, habitData: HabitData) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_HABIT_NAME, habitData.name)
            putString(KEY_HABIT_GOAL, habitData.goal)
            putBoolean(KEY_HABIT_COMPLETED, habitData.completed)
        }.apply()
    }

    fun loadHabit(context: Context): HabitData {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_HABIT_NAME, "") ?: ""
        val goal = prefs.getString(KEY_HABIT_GOAL, "") ?: ""
        val completed = prefs.getBoolean(KEY_HABIT_COMPLETED, false)
        return HabitData(name, goal, completed)
    }

    fun clearHabit(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            remove(KEY_HABIT_NAME)
            remove(KEY_HABIT_GOAL)
        }.apply()
    }
}
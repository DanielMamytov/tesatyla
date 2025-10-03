package sr.otaryp.tesatyla.data.preferences

import android.content.Context
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FocusPreferences {

    private const val PREFS_NAME = "focus_prefs"
    private const val KEY_DATE = "focus_date"
    private const val KEY_COUNT = "focus_count"

    private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

    private fun todayKey(): String = dateFormat.format(Date())

    private fun sharedPreferences(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getTodayCount(context: Context): Int {
        val preferences = sharedPreferences(context)
        val savedDate = preferences.getString(KEY_DATE, null)
        return if (savedDate == todayKey()) {
            preferences.getInt(KEY_COUNT, 0)
        } else {
            0
        }
    }

    fun incrementTodayCount(context: Context): Int {
        val preferences = sharedPreferences(context)
        val today = todayKey()
        val newCount = if (preferences.getString(KEY_DATE, null) == today) {
            preferences.getInt(KEY_COUNT, 0) + 1
        } else {
            1
        }
        preferences.edit {
            putString(KEY_DATE, today)
            putInt(KEY_COUNT, newCount)
        }
        return newCount
    }

    fun ensureTodayCount(context: Context): Int {
        val preferences = sharedPreferences(context)
        val today = todayKey()
        val savedDate = preferences.getString(KEY_DATE, null)
        return if (savedDate == today) {
            preferences.getInt(KEY_COUNT, 0)
        } else {
            preferences.edit {
                putString(KEY_DATE, today)
                putInt(KEY_COUNT, 0)
            }
            0
        }
    }
}

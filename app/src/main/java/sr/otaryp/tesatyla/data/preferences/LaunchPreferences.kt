package sr.otaryp.tesatyla.data.preferences

import android.content.Context
import androidx.core.content.edit

object LaunchPreferences {

    private const val PREFS_NAME = "launch_preferences"
    private const val KEY_ONBOARDING_COMPLETE = "onboarding_complete"
    private const val KEY_SPLASH_SHOWN = "splash_shown"

    fun isOnboardingComplete(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

    fun setOnboardingComplete(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_ONBOARDING_COMPLETE, true)
        }
    }

    fun isSplashShown(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_SPLASH_SHOWN, false)
    }

    fun setSplashShown(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putBoolean(KEY_SPLASH_SHOWN, true)
        }
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            clear()
        }
    }
}

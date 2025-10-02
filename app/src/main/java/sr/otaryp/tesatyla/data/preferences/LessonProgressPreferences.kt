package sr.otaryp.tesatyla.data.preferences

import android.content.Context
import androidx.core.content.edit

data class LessonProgress(
    val lessonId: Int,
    val lessonTitle: String
)

object LessonProgressPreferences {

    private const val PREFS_NAME = "lesson_progress"
    private const val KEY_LESSON_ID = "lesson_id"
    private const val KEY_LESSON_TITLE = "lesson_title"

    fun getCurrentLesson(context: Context): LessonProgress? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lessonId = sharedPreferences.getInt(KEY_LESSON_ID, -1)
        val lessonTitle = sharedPreferences.getString(KEY_LESSON_TITLE, null)
        return if (lessonId != -1 && !lessonTitle.isNullOrBlank()) {
            LessonProgress(lessonId, lessonTitle)
        } else {
            null
        }
    }

    fun setCurrentLesson(context: Context, lessonId: Int, lessonTitle: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putInt(KEY_LESSON_ID, lessonId)
            putString(KEY_LESSON_TITLE, lessonTitle)
        }
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            remove(KEY_LESSON_ID)
            remove(KEY_LESSON_TITLE)
        }
    }
}

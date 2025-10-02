package sr.otaryp.tesatyla.presentation.ui.lessons

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sr.otaryp.tesatyla.data.lessons.LessonRepository

class LessonListViewModel(
    private val repository: LessonRepository
) : ViewModel() {

    val lessons: StateFlow<List<LessonListItem>> = repository
        .observeLessons()
        .map { lessonWithSteps ->
            lessonWithSteps.map { lesson ->
                val entity = lesson.lesson
                LessonListItem(
                    id = entity.id,
                    title = entity.title.replaceFirst(LESSON_TITLE_PREFIX_REGEX, "").trim(),
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    companion object {
        private val LESSON_TITLE_PREFIX_REGEX = Regex("^Lesson \\d+:\\s*")

        fun provideFactory(context: Context): ViewModelProvider.Factory {
            val appContext = context.applicationContext
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = LessonRepository.getInstance(appContext)
                    if (modelClass.isAssignableFrom(LessonListViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return LessonListViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}

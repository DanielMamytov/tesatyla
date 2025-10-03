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

class LessonDetailViewModel(
    private val repository: LessonRepository,
    private val lessonId: Int
) : ViewModel() {

    val uiState: StateFlow<LessonDetailUiState> = repository
        .observeLesson(lessonId)
        .map { lessonWithSteps ->
            val lesson = lessonWithSteps.lesson
            val sortedSteps = lessonWithSteps.steps.sortedBy { it.number }
            val stepItems = sortedSteps.map { step ->
                LessonStepItem(
                    id = step.id,
                    lessonId = lesson.id,
                    stepNumber = step.number,
                    title = step.title,
                    theoryPreview = step.theory.take(160),
                    isCompleted = step.isCompleted
                )
            }
            val completedSteps = stepItems.count { it.isCompleted }
            LessonDetailUiState(
                lessonId = lesson.id,
                title = lesson.title,
                description = lesson.description,
                teaching = lesson.teaching,
                steps = stepItems,
                completedSteps = completedSteps,
                totalSteps = stepItems.size,
                isCompleted = stepItems.isNotEmpty() && completedSteps == stepItems.size
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LessonDetailUiState(lessonId = lessonId)
        )

    companion object {
        fun provideFactory(context: Context, lessonId: Int): ViewModelProvider.Factory {
            val appContext = context.applicationContext
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = LessonRepository.getInstance(appContext)
                    if (modelClass.isAssignableFrom(LessonDetailViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return LessonDetailViewModel(repository, lessonId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}

data class LessonDetailUiState(
    val lessonId: Int,
    val title: String = "",
    val description: String = "",
    val teaching: String = "",
    val steps: List<LessonStepItem> = emptyList(),
    val completedSteps: Int = 0,
    val totalSteps: Int = 0,
    val isCompleted: Boolean = false
)

data class LessonStepItem(
    val id: Int,
    val lessonId: Int,
    val stepNumber: Int,
    val title: String,
    val theoryPreview: String,
    val isCompleted: Boolean
)

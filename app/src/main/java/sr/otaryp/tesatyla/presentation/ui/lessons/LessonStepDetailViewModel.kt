package sr.otaryp.tesatyla.presentation.ui.lessons

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.data.lessons.LessonRepository
import sr.otaryp.tesatyla.data.lessons.StepCompletionResult

class LessonStepDetailViewModel(
    private val repository: LessonRepository,
    private val lessonId: Int,
    private val stepId: Int
) : ViewModel() {

    private val eventsFlow = MutableSharedFlow<LessonStepDetailEvent>()
    val events: SharedFlow<LessonStepDetailEvent> = eventsFlow

    val uiState: StateFlow<LessonStepDetailUiState> = combine(
        repository.observeStep(stepId),
        repository.observeLesson(lessonId)
    ) { step, lessonWithSteps ->
        val lesson = lessonWithSteps.lesson
        val remainingIncomplete = lessonWithSteps.steps.count { !it.isCompleted }
        LessonStepDetailUiState(
            lessonId = lesson.id,
            lessonTitle = lesson.title,
            stepId = step.id,
            stepNumber = step.number,
            stepTitle = step.title,
            theory = step.theory,
            practice = step.practice,
            isCompleted = step.isCompleted,
            isLastIncompleteStep = !step.isCompleted && remainingIncomplete <= 1
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LessonStepDetailUiState(lessonId = lessonId, stepId = stepId)
    )

    fun onCompleteStep() {
        viewModelScope.launch {
            when (repository.completeStep(lessonId, stepId)) {
                StepCompletionResult.StepCompleted ->
                    eventsFlow.emit(LessonStepDetailEvent.StepCompleted)

                StepCompletionResult.LessonCompleted -> {
                    val nextLessonId = repository.getNextLessonId(lessonId)
                    val nextLessonTitle = nextLessonId?.let { repository.getLessonById(it)?.title }
                    val currentLessonTitle = uiState.value.lessonTitle
                    eventsFlow.emit(
                        LessonStepDetailEvent.LessonCompleted(
                            lessonId = lessonId,
                            lessonTitle = currentLessonTitle,
                            nextLessonId = nextLessonId,
                            nextLessonTitle = nextLessonTitle
                        )
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
            lessonId: Int,
            stepId: Int
        ): ViewModelProvider.Factory {
            val appContext = context.applicationContext
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = LessonRepository.getInstance(appContext)
                    if (modelClass.isAssignableFrom(LessonStepDetailViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return LessonStepDetailViewModel(repository, lessonId, stepId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}

data class LessonStepDetailUiState(
    val lessonId: Int,
    val lessonTitle: String = "",
    val stepId: Int,
    val stepNumber: Int = 0,
    val stepTitle: String = "",
    val theory: String = "",
    val practice: String = "",
    val isCompleted: Boolean = false,
    val isLastIncompleteStep: Boolean = false
)

sealed interface LessonStepDetailEvent {
    object StepCompleted : LessonStepDetailEvent
    data class LessonCompleted(
        val lessonId: Int,
        val lessonTitle: String,
        val nextLessonId: Int?,
        val nextLessonTitle: String?
    ) : LessonStepDetailEvent
}

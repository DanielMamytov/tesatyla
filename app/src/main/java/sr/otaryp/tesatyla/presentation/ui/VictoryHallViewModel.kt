package sr.otaryp.tesatyla.presentation.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.data.lessons.LessonRepository

class VictoryHallViewModel(
    private val repository: LessonRepository,
    private val lessonId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(VictoryHallUiState(lessonId = lessonId))
    val uiState: StateFlow<VictoryHallUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<VictoryHallEvent>()
    val events: SharedFlow<VictoryHallEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            val lesson = repository.getLessonById(lessonId)
            val nextLessonId = repository.getNextLessonId(lessonId)
            val nextLessonTitle = nextLessonId?.let { repository.getLessonById(it)?.title }
            _uiState.value = VictoryHallUiState(
                lessonId = lessonId,
                lessonTitle = lesson?.title.orEmpty(),
                nextLessonId = nextLessonId,
                nextLessonTitle = nextLessonTitle
            )
        }
    }

    fun replayLesson() {
        viewModelScope.launch {
            repository.resetLesson(lessonId)
            val lessonTitle = repository.getLessonById(lessonId)?.title
                ?: _uiState.value.lessonTitle
            _events.emit(VictoryHallEvent.LessonReset(lessonId, lessonTitle))
        }
    }

    companion object {
        fun provideFactory(context: Context, lessonId: Int): ViewModelProvider.Factory {
            val appContext = context.applicationContext
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = LessonRepository.getInstance(appContext)
                    if (modelClass.isAssignableFrom(VictoryHallViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return VictoryHallViewModel(repository, lessonId) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}

data class VictoryHallUiState(
    val lessonId: Int,
    val lessonTitle: String = "",
    val nextLessonId: Int? = null,
    val nextLessonTitle: String? = null
)

sealed interface VictoryHallEvent {
    data class LessonReset(val lessonId: Int, val lessonTitle: String) : VictoryHallEvent
}

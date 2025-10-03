package sr.otaryp.tesatyla.presentation.ui.progress

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import sr.otaryp.tesatyla.data.lessons.LessonRepository
import sr.otaryp.tesatyla.data.lessons.SkillCatalog
import sr.otaryp.tesatyla.data.preferences.FocusPreferences

class ProgressViewModel(
    private val repository: LessonRepository,
) : ViewModel() {

    private val pomodoroCount = MutableStateFlow(0)

    private val lessonProgress = repository.observeLessons()
        .map { lessons ->
            val totalSteps = lessons.sumOf { it.steps.size }
            val completedSteps = lessons.sumOf { lesson ->
                lesson.steps.count { it.isCompleted }
            }

            val skills = SkillCatalog.skills.mapNotNull { skill ->
                val skillLessons = lessons.filter { skill.lessonIds.contains(it.lesson.id) }
                if (skillLessons.isEmpty()) return@mapNotNull null

                val total = skillLessons.size
                val completed = skillLessons.count { it.lesson.isCompleted }
                val isComplete = total > 0 && completed == total
                SkillProgressItem(
                    id = skill.id,
                    title = skill.title,
                    completedSteps = completed,
                    totalSteps = total,
                    completionPercent = if (total == 0) 0 else ((completed.toFloat() / total) * 100).roundToInt(),
                    isComplete = isComplete,
                )
            }

            LessonProgressSnapshot(
                completedSteps = completedSteps,
                totalSteps = totalSteps,
                overallPercent = if (totalSteps == 0) 0 else ((completedSteps.toFloat() / totalSteps) * 100).roundToInt(),
                skills = skills,
            )
        }

    val uiState: StateFlow<ProgressUiState> = combine(lessonProgress, pomodoroCount) { progress, pomodoros ->
        ProgressUiState(
            overallPercent = progress.overallPercent,
            completedSteps = progress.completedSteps,
            totalSteps = progress.totalSteps,
            skills = progress.skills,
            pomodoroCycles = pomodoros,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ProgressUiState(),
    )

    fun refreshPomodoroCount(context: Context) {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) {
                FocusPreferences.ensureTodayCount(context)
            }
            pomodoroCount.update { count }
        }
    }

    private data class LessonProgressSnapshot(
        val completedSteps: Int,
        val totalSteps: Int,
        val overallPercent: Int,
        val skills: List<SkillProgressItem>,
    )

    companion object {
        const val DAILY_POMODORO_GOAL = 12

        fun provideFactory(context: Context): ViewModelProvider.Factory {
            val appContext = context.applicationContext
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val repository = LessonRepository.getInstance(appContext)
                    return ProgressViewModel(repository) as T
                }
            }
        }
    }
}

data class ProgressUiState(
    val overallPercent: Int = 0,
    val completedSteps: Int = 0,
    val totalSteps: Int = 0,
    val skills: List<SkillProgressItem> = emptyList(),
    val pomodoroCycles: Int = 0,
)

data class SkillProgressItem(
    val id: String,
    val title: String,
    val completedSteps: Int,
    val totalSteps: Int,
    val completionPercent: Int,
    val isComplete: Boolean,
)


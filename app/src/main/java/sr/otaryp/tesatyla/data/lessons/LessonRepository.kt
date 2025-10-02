package sr.otaryp.tesatyla.data.lessons

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LessonRepository private constructor(
    private val lessonDao: LessonDao
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch { prepopulateIfNeeded() }
    }

    fun observeLessons(): Flow<List<LessonWithSteps>> = lessonDao.getLessonsWithSteps()

    fun observeLesson(lessonId: Int): Flow<LessonWithSteps> = lessonDao.getLessonWithSteps(lessonId)

    fun observeLessonSteps(lessonId: Int): Flow<List<LessonStepEntity>> =
        lessonDao.getStepsForLesson(lessonId)

    fun observeStep(stepId: Int): Flow<LessonStepEntity> = lessonDao.getStep(stepId)

    suspend fun completeStep(lessonId: Int, stepId: Int): StepCompletionResult {
        lessonDao.updateStepCompletion(stepId, true)
        val totalSteps = lessonDao.countStepsForLesson(lessonId)
        val completedSteps = lessonDao.countCompletedStepsForLesson(lessonId)
        val isLessonCompleted = completedSteps == totalSteps && totalSteps > 0
        lessonDao.updateLessonCompletion(lessonId, isLessonCompleted)
        return if (isLessonCompleted) {
            StepCompletionResult.LessonCompleted
        } else {
            StepCompletionResult.StepCompleted
        }
    }

    suspend fun resetLesson(lessonId: Int) {
        lessonDao.resetStepsForLesson(lessonId)
        lessonDao.updateLessonCompletion(lessonId, false)
    }

    suspend fun getNextIncompleteStep(lessonId: Int): LessonStepEntity? =
        lessonDao.getNextIncompleteStep(lessonId)

    suspend fun getNextLessonId(currentLessonId: Int): Int? =
        lessonDao.findNextLessonId(currentLessonId)

    suspend fun getLessonById(lessonId: Int): LessonEntity? = lessonDao.getLessonById(lessonId)

    private suspend fun prepopulateIfNeeded() {
        if (lessonDao.countLessons() > 0) return

        val lessons = LessonSeedData.lessons.map { seed ->
            LessonEntity(
                id = seed.id,
                title = seed.title,
                description = seed.description,
                teaching = seed.teaching
            )
        }
        val steps = LessonSeedData.lessons.flatMap { lesson ->
            lesson.steps.map { step ->
                LessonStepEntity(
                    id = lesson.id * 10 + step.number,
                    lessonId = lesson.id,
                    number = step.number,
                    title = step.title,
                    theory = step.theory,
                    practice = step.practice
                )
            }
        }
        lessonDao.insertLessons(lessons)
        lessonDao.insertSteps(steps)
    }

    companion object {
        @Volatile
        private var INSTANCE: LessonRepository? = null

        fun getInstance(context: Context): LessonRepository {
            return INSTANCE ?: synchronized(this) {
                val database = LessonDatabase.getInstance(context)
                LessonRepository(database.lessonDao()).also { INSTANCE = it }
            }
        }
    }
}

sealed interface StepCompletionResult {
    object StepCompleted : StepCompletionResult
    object LessonCompleted : StepCompletionResult
}

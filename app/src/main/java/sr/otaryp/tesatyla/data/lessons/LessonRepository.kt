package sr.otaryp.tesatyla.data.lessons

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


class LessonRepository private constructor(
    private val lessonDao: LessonDao
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val prepopulateJob = scope.async { prepopulateIfNeeded() }

    fun observeLessons(): Flow<List<LessonWithSteps>> = flow {
        prepopulateJob.await()
        emitAll(lessonDao.getLessonsWithSteps())
    }

    fun observeLesson(lessonId: Int): Flow<LessonWithSteps> = flow {
        prepopulateJob.await()
        emitAll(lessonDao.getLessonWithSteps(lessonId))
    }

    fun observeLessonSteps(lessonId: Int): Flow<List<LessonStepEntity>> = flow {
        prepopulateJob.await()
        emitAll(lessonDao.getStepsForLesson(lessonId))
    }

    fun observeStep(stepId: Int): Flow<LessonStepEntity> = flow {
        prepopulateJob.await()
        emitAll(lessonDao.getStep(stepId))
    }

    suspend fun completeStep(lessonId: Int, stepId: Int): StepCompletionResult {
        prepopulateJob.await()

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
        prepopulateJob.await()

        lessonDao.resetStepsForLesson(lessonId)
        lessonDao.updateLessonCompletion(lessonId, false)
    }

    suspend fun resetAllProgress() {
        prepopulateJob.await()

        lessonDao.resetAllSteps()
        lessonDao.resetAllLessons()
    }

    suspend fun getNextIncompleteStep(lessonId: Int): LessonStepEntity? {
        prepopulateJob.await()
        return lessonDao.getNextIncompleteStep(lessonId)
    }

    suspend fun getNextLessonId(currentLessonId: Int): Int? {
        prepopulateJob.await()
        return lessonDao.findNextLessonId(currentLessonId)
    }

    suspend fun getLessonById(lessonId: Int): LessonEntity? {
        prepopulateJob.await()
        return lessonDao.getLessonById(lessonId)
    }


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
    // Пример в LessonRepository (или отдельный Seeder)
    private fun mapLessonSeedsToEntities(seeds: List<LessonSeed>): Pair<List<LessonEntity>, List<LessonStepEntity>> {
        val lessons = seeds.map { seed ->
            LessonEntity(
                id = seed.id,
                title = seed.title,
                description = seed.description,
                teaching = seed.teaching,
                isCompleted = false
            )
        }
        val steps = seeds.flatMap { lesson ->
            lesson.steps.map { step ->
                // Уникальный ID (с запасом): lessonId * 100 + number
                val uniqueStepId = lesson.id * 100 + step.number
                LessonStepEntity(
                    id = uniqueStepId,
                    lessonId = lesson.id,
                    number = step.number,
                    title = step.title,
                    theory = step.theory,
                    practice = step.practice,
                    isCompleted = false
                )
            }
        }
        return lessons to steps
    }

    suspend fun seedIfEmpty() {
        if (lessonDao.countLessons() == 0) {
            val (lessons, steps) = mapLessonSeedsToEntities(LessonSeedData.lessons)
            lessonDao.insertLessons(lessons)
            lessonDao.insertSteps(steps)
        }
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

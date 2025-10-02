package sr.otaryp.tesatyla.data.lessons

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Transaction
    @Query("SELECT * FROM lessons ORDER BY id")
    fun getLessonsWithSteps(): Flow<List<LessonWithSteps>>

    @Transaction
    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    fun getLessonWithSteps(lessonId: Int): Flow<LessonWithSteps>

    @Query("SELECT * FROM lesson_steps WHERE lessonId = :lessonId ORDER BY number")
    fun getStepsForLesson(lessonId: Int): Flow<List<LessonStepEntity>>

    @Query("SELECT * FROM lesson_steps WHERE id = :stepId")
    fun getStep(stepId: Int): Flow<LessonStepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<LessonStepEntity>)

    @Query("UPDATE lesson_steps SET isCompleted = :isCompleted WHERE id = :stepId")
    suspend fun updateStepCompletion(stepId: Int, isCompleted: Boolean)

    @Query("UPDATE lessons SET isCompleted = :isCompleted WHERE id = :lessonId")
    suspend fun updateLessonCompletion(lessonId: Int, isCompleted: Boolean)

    @Query("SELECT COUNT(*) FROM lessons")
    suspend fun countLessons(): Int

    @Query("SELECT COUNT(*) FROM lesson_steps WHERE lessonId = :lessonId")
    suspend fun countStepsForLesson(lessonId: Int): Int

    @Query("SELECT COUNT(*) FROM lesson_steps WHERE lessonId = :lessonId AND isCompleted = 1")
    suspend fun countCompletedStepsForLesson(lessonId: Int): Int

    @Query("SELECT id FROM lessons WHERE id > :lessonId ORDER BY id LIMIT 1")
    suspend fun findNextLessonId(lessonId: Int): Int?

    @Query("SELECT * FROM lessons WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Int): LessonEntity?

    @Query("UPDATE lesson_steps SET isCompleted = 0 WHERE lessonId = :lessonId")
    suspend fun resetStepsForLesson(lessonId: Int)

    @Query("SELECT * FROM lesson_steps WHERE lessonId = :lessonId AND isCompleted = 0 ORDER BY number LIMIT 1")
    suspend fun getNextIncompleteStep(lessonId: Int): LessonStepEntity?
}

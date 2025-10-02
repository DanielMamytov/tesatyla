package sr.otaryp.tesatyla.data.lessons

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_steps")
data class LessonStepEntity(
    @PrimaryKey val id: Int,
    val lessonId: Int,
    val number: Int,
    val title: String,
    val theory: String,
    val practice: String,
    val isCompleted: Boolean = false
)

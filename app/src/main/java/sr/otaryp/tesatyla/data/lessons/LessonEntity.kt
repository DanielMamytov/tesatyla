package sr.otaryp.tesatyla.data.lessons

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val teaching: String,
    val isCompleted: Boolean = false
)

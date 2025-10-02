package sr.otaryp.tesatyla.data.lessons

import androidx.room.Embedded
import androidx.room.Relation

data class LessonWithSteps(
    @Embedded val lesson: LessonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "lessonId"
    )
    val steps: List<LessonStepEntity>
)

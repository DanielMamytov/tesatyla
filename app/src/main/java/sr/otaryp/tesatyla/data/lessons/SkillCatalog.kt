package sr.otaryp.tesatyla.data.lessons

/**
 * Provides groupings of lessons into broader skill themes that can be
 * displayed on the progress screen. Each skill contains the ids of the
 * lessons that reinforce it so that progress can be aggregated per skill
 * and filtered in the lesson list screen.
 */
object SkillCatalog {

    data class Skill(
        val id: String,
        val title: String,
        val lessonIds: List<Int>,
    )

    val skills: List<Skill> = LessonSeedData.lessons.map { lesson ->
        Skill(
            id = lesson.id.toString(),
            title = lesson.title.substringAfter(": ", lesson.title).trim(),
            lessonIds = listOf(lesson.id),
        )
    }

    fun findSkill(skillId: String?): Skill? {
        if (skillId.isNullOrBlank()) return null
        return skills.firstOrNull { it.id == skillId }
    }
}


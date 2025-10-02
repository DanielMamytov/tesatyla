package sr.otaryp.tesatyla.presentation.ui.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentLessonListBinding
import kotlin.LazyThreadSafetyMode

class LessonListFragment : Fragment() {

    private var _binding: FragmentLessonListBinding? = null
    private val binding get() = _binding!!

    private val lessonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        LessonAdapter(::onLessonSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        lessonAdapter.submitList(lessonExamples)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        binding.rvListLessons.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lessonAdapter
            setHasFixedSize(true)
        }
    }

    private fun onLessonSelected(lesson: LessonListItem) {
        LessonProgressPreferences.setCurrentLesson(
            requireContext(),
            lesson.id,
            lesson.title
        )
        findNavController().navigate(R.id.lessonDetailFragment)
    }

    private val lessonExamples by lazy(LazyThreadSafetyMode.NONE) {
        listOf(
            LessonListItem(
                id = 1,
                title = getString(R.string.lesson_title_pomodoro_trials),
                description = getString(R.string.lesson_description_pomodoro_trials),
                isCompleted = true
            ),
            LessonListItem(
                id = 2,
                title = getString(R.string.lesson_title_scrolls_of_order),
                description = getString(R.string.lesson_description_scrolls_of_order),
                isCompleted = true
            ),
            LessonListItem(
                id = 3,
                title = getString(R.string.lesson_title_defend_against_distractions),
                description = getString(R.string.lesson_description_defend_against_distractions),
                isCompleted = false
            ),
            LessonListItem(
                id = 4,
                title = getString(R.string.lesson_title_timekeepers_path),
                description = getString(R.string.lesson_description_timekeepers_path),
                isCompleted = false
            ),
            LessonListItem(
                id = 5,
                title = getString(R.string.lesson_title_procrastination_dragon),
                description = getString(R.string.lesson_description_procrastination_dragon),
                isCompleted = false
            ),
            LessonListItem(
                id = 6,
                title = getString(R.string.lesson_title_focus_forge),
                description = getString(R.string.lesson_description_focus_forge),
                isCompleted = false
            ),
            LessonListItem(
                id = 7,
                title = getString(R.string.lesson_title_energy_management),
                description = getString(R.string.lesson_description_energy_management),
                isCompleted = false
            ),
            LessonListItem(
                id = 8,
                title = getString(R.string.lesson_title_task_alchemy),
                description = getString(R.string.lesson_description_task_alchemy),
                isCompleted = false
            ),
            LessonListItem(
                id = 9,
                title = getString(R.string.lesson_title_habit_loop),
                description = getString(R.string.lesson_description_habit_loop),
                isCompleted = false
            ),
            LessonListItem(
                id = 10,
                title = getString(R.string.lesson_title_reflection_chamber),
                description = getString(R.string.lesson_description_reflection_chamber),
                isCompleted = false
            )
        )
    }
}


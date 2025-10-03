package sr.otaryp.tesatyla.presentation.ui.lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlin.LazyThreadSafetyMode
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.data.lessons.SkillCatalog
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentLessonListBinding
import sr.otaryp.tesatyla.R

class LessonListFragment : Fragment() {

    private var _binding: FragmentLessonListBinding? = null
    private val binding get() = _binding!!

    private val lessonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        LessonAdapter(::onLessonSelected)
    }

    private val viewModel: LessonListViewModel by viewModels {
        LessonListViewModel.provideFactory(requireContext())
    }

    private val args: LessonListFragmentArgs by navArgs()

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
        observeLessons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val skill = SkillCatalog.findSkill(args.skillId)
        binding.tvTitle.text = skill?.title ?: getString(R.string.lesson_list_title_all)
    }

    private fun setupRecyclerView() {
        binding.rvListLessons.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lessonAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeLessons() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lessons.collect { lessons ->
                    val skill = SkillCatalog.findSkill(args.skillId)
                    val filtered = if (skill != null) {
                        lessons.filter { lesson -> skill.lessonIds.contains(lesson.id) }
                    } else {
                        lessons
                    }
                    lessonAdapter.submitList(filtered)
                }
            }
        }
    }

    private fun onLessonSelected(lesson: LessonListItem) {
        LessonProgressPreferences.setCurrentLesson(
            requireContext(),
            lesson.id,
            lesson.title
        )
        val directions = LessonListFragmentDirections
            .actionNavLessonsToLessonDetailFragment(lesson.id)
        findNavController().navigate(directions)
    }
}

package sr.otaryp.tesatyla.presentation.ui.progress

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
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProgressViewModel by viewModels {
        ProgressViewModel.provideFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        observeState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshPomodoroCount(requireContext())
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::renderState)
            }
        }
    }

    private fun renderState(state: ProgressUiState) = with(binding) {
        progressPercentage.text = getString(R.string.progress_percentage_format, state.overallPercent)
        progressPercentage.contentDescription = getString(
            R.string.progress_overall_content_description,
            state.overallPercent,
            state.completedLessons,
            state.totalLessons,
        )
        pomodoroCycles.text = getString(R.string.progress_cycles_format, state.pomodoroCycles)

        val highlightedSkill = state.skills.firstOrNull()

        if (highlightedSkill != null) {
            skillTitle.text = highlightedSkill.title
            skillLessons.text = getString(
                R.string.progress_lessons_completed,
                highlightedSkill.completedLessons,
                highlightedSkill.totalLessons,
            )
            skillPercent.text = getString(
                R.string.progress_percentage_format,
                highlightedSkill.completionPercent,
            )
            progressBar.progress = highlightedSkill.completionPercent
            skillCardRoot.contentDescription = getString(
                R.string.progress_skill_content_description,
                highlightedSkill.title,
                highlightedSkill.completedLessons,
                highlightedSkill.totalLessons,
                highlightedSkill.completionPercent,
            )
            skillCardRoot.setOnClickListener { onSkillSelected(highlightedSkill) }
            skillCardRoot.isEnabled = true
            skillCardRoot.isClickable = true
            skillCardRoot.isFocusable = true
        } else {
            skillTitle.text = getString(R.string.progress_no_skills_title)
            skillLessons.text = getString(R.string.progress_no_skills)
            skillPercent.text = getString(R.string.progress_percentage_format, 0)
            progressBar.progress = 0
            skillCardRoot.setOnClickListener(null)
            skillCardRoot.isEnabled = false
            skillCardRoot.isClickable = false
            skillCardRoot.isFocusable = false
            skillCardRoot.contentDescription = getString(R.string.progress_no_skills)
        }
    }

    private fun onSkillSelected(item: SkillProgressItem) {
        val directions = ProgressFragmentDirections
            .actionNavProgressToNavLessons(item.id)
        findNavController().navigate(directions)
    }
}

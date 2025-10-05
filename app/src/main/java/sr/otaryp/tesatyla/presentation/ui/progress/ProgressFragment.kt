package sr.otaryp.tesatyla.presentation.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.databinding.FragmentProgressBinding
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val skillAdapter by lazy { SkillProgressAdapter(::onSkillSelected) }
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
        setupSkillList()
        observeState()

        binding.pomodoroCycles.applyVerticalGradient()
        binding.skillMasteryTitle.applyVerticalGradient()
        binding.textProgressPercentage.applyVerticalGradient()
        binding.textProgressTitle.applyVerticalGradient()
//        binding.pomodoroProgress.max = ProgressViewModel.DAILY_POMODORO_GOAL
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
        binding.imageSettings.setOnClickListener {
            findNavController().navigate(R.id.action_global_settingsFragment)
        }
    }

    private fun setupSkillList() {
        binding.skillProgressList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = skillAdapter
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
        textProgressPercentage.text = getString(R.string.progress_percentage_format, state.overallPercent)
        circularProgressBar.animateProgress(state.overallPercent.toFloat())
//        textLessonsCompleted.text = getString(
//            R.string.progress_lessons_completed,
//            state.completedSteps,
//            state.totalSteps,
//        )
        pomodoroCycles.text = getString(R.string.progress_cycles_format, state.pomodoroCycles)

        skillAdapter.submitList(state.skills)
        skillProgressList.isVisible = state.skills.isNotEmpty()
        textNoSkills.isVisible = state.skills.isEmpty()
    }

    private fun onSkillSelected(item: SkillProgressItem) {
        val directions = ProgressFragmentDirections
            .actionNavProgressToNavLessons(item.id)
        findNavController().navigate(directions)
    }
}

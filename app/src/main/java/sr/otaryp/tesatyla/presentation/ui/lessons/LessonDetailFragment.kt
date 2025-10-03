package sr.otaryp.tesatyla.presentation.ui.lessons

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentLessonDetailBinding

class LessonDetailFragment : Fragment() {

    private val args: LessonDetailFragmentArgs by navArgs()

    private var _binding: FragmentLessonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LessonDetailViewModel by viewModels {
        LessonDetailViewModel.provideFactory(requireContext(), args.lessonId)
    }

    private val stepsAdapter = LessonStepsAdapter(::onStepSelected)
    private var currentState: LessonDetailUiState? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupCallToAction()
        observeUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupRecyclerView() {
        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stepsAdapter
            setHasFixedSize(false)
        }
    }

    private fun setupCallToAction() {
//        binding.btnLessonCta.setOnClickListener {
//            currentState?.let { state ->
//                if (state.isCompleted) {
//                    val directions = LessonDetailFragmentDirections
//                        .actionLessonDetailFragmentToVictoryHallFragment(state.lessonId)
//                    findNavController().navigate(directions)
//                } else {
//                    val nextStep = state.steps.firstOrNull { !it.isCompleted }
//                    if (nextStep != null) {
//                        onStepSelected(nextStep)
//                    }
//                }
//            }
//        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    currentState = state
                    binding.tvLessonTitle.text = state.title
                    binding.tvLessonDescription.text = state.description
                    binding.tvLessonTeaching.text = state.teaching
//                    binding.ivLessonStatus.setImageResource(
//                        if (state.isCompleted) R.drawable.shield_bg else R.drawable.shield_gray
//                    )
//                    binding.tvLessonStatus.setText(
//                        if (state.isCompleted) {
//                            R.string.lesson_detail_progress_completed
//                        } else {
//                            R.string.lesson_detail_progress_in_progress
//                        }
//                    )
//                    binding.tvLessonProgress.text = getString(
//                        R.string.lesson_detail_progress_template,
//                        state.completedSteps,
//                        state.totalSteps
//                    )
                    val hasSteps = state.totalSteps > 0
                    val hasAccessibleStep = state.steps.any { !it.isLocked && !it.isCompleted }
//                    binding.tvLessonProgress.isVisible = hasSteps
//                    binding.progressContainer.isVisible = hasSteps
//                    binding.btnLessonCta.setText(
//                        if (state.isCompleted) {
//                            R.string.lesson_detail_cta_victory
//                        } else {
//                            R.string.lesson_detail_cta_complete
//                        }
//                    )
//                    binding.btnLessonCta.isEnabled = state.isCompleted || hasAccessibleStep
//                    binding.btnLessonCta.alpha = if (state.isCompleted || hasAccessibleStep) 1f else 0.6f
                    stepsAdapter.submitList(state.steps)

                    LessonProgressPreferences.setCurrentLesson(
                        requireContext(),
                        state.lessonId,
                        state.title
                    )
                }
            }
        }
    }

    private fun onStepSelected(step: LessonStepItem) {
        if (step.isLocked) return
        val directions = LessonDetailFragmentDirections
            .actionLessonDetailFragmentToLessonStepDetailFragment(
                lessonId = step.lessonId,
                stepId = step.id
            )
        findNavController().navigate(directions)
    }
}

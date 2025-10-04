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
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentLessonStepDetailBinding

class LessonStepDetailFragment : Fragment() {

    private val args: LessonStepDetailFragmentArgs by navArgs()

    private var _binding: FragmentLessonStepDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LessonStepDetailViewModel by viewModels {
        LessonStepDetailViewModel.provideFactory(
            requireContext(),
            args.lessonId,
            args.stepId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonStepDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupButton()
        observeUiState()
        observeEvents()

        binding.tvLessonTitle.applyVerticalGradient()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupButton() {
        binding.btnCompleteQuest.setOnClickListener {
            viewModel.onCompleteStep()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvLessonTitle.text = state.lessonTitle
                    val formattedTitle = if (state.stepNumber > 0 && state.stepTitle.isNotBlank()) {
                        getString(
                            R.string.lesson_step_detail_title_format,
                            state.stepNumber,
                            state.stepTitle
                        )
                    } else {
                        state.stepTitle
                    }
                    binding.tvStepTitle.text = formattedTitle
                    binding.tvTheory.text = state.theory
                    binding.tvPractice.text = state.practice

                    if (state.isCompleted) {
                        binding.tvStepStatus.isVisible = true
                        binding.tvStepStatus.setText(R.string.lesson_step_completed_message)
                        binding.btnCompleteQuest.isEnabled = false
                        binding.btnCompleteQuest.text = getString(R.string.lesson_step_completed_button)
                    } else {
                        binding.tvStepStatus.isVisible = false
                        binding.btnCompleteQuest.isEnabled = true
                        binding.btnCompleteQuest.text = getString(R.string.lesson_step_complete)
                    }
                    binding.btnCompleteQuest.alpha =
                        if (binding.btnCompleteQuest.isEnabled) 1f else 0.6f
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        LessonStepDetailEvent.StepCompleted -> {
                            findNavController().popBackStack()
                        }

                        is LessonStepDetailEvent.LessonCompleted -> {
                            val context = requireContext()
                            if (event.nextLessonId != null && event.nextLessonTitle != null) {
                                LessonProgressPreferences.setCurrentLesson(
                                    context,
                                    event.nextLessonId,
                                    event.nextLessonTitle
                                )
                            } else {
                                LessonProgressPreferences.clear(context)
                            }

                            val directions = LessonStepDetailFragmentDirections
                                .actionLessonStepDetailFragmentToVictoryHallFragment(
                                    lessonId = event.lessonId
                                )
                            findNavController().navigate(directions)
                        }
                    }
                }
            }
        }
    }
}

package sr.otaryp.tesatyla.presentation.ui

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
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentVictoryHallBinding

class VictoryHallFragment : Fragment() {

    private val args: VictoryHallFragmentArgs by navArgs()

    private var _binding: FragmentVictoryHallBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VictoryHallViewModel by viewModels {
        VictoryHallViewModel.provideFactory(requireContext(), args.lessonId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVictoryHallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupButtons()
        observeUiState()
        observeEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupButtons() {
        binding.btnNextLesson.isEnabled = false
        binding.btnReplayLesson.setOnClickListener {
            viewModel.replayLesson()
        }
        binding.btnNextLesson.setOnClickListener {
            val state = viewModel.uiState.value
            val nextLessonId = state.nextLessonId
            if (nextLessonId != null) {
                val nextTitle = state.nextLessonTitle ?: state.lessonTitle
                LessonProgressPreferences.setCurrentLesson(
                    requireContext(),
                    nextLessonId,
                    nextTitle
                )
                val directions = VictoryHallFragmentDirections
                    .actionVictoryHallFragmentToLessonDetailFragment(nextLessonId)
                findNavController().navigate(directions)
            } else {
                LessonProgressPreferences.clear(requireContext())
                findNavController().navigate(R.id.nav_lessons)
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    val lessonTitle = state.lessonTitle
                    val titleText = if (lessonTitle.isNotBlank()) {
                        getString(R.string.victory_title_template, lessonTitle)
                    } else {
                        getString(R.string.victory_generic_title)
                    }
                    val messageText = if (lessonTitle.isNotBlank()) {
                        getString(R.string.victory_message_template, lessonTitle)
                    } else {
                        getString(R.string.victory_generic_message)
                    }
                    binding.tvVictoryTitle.text = titleText
                    binding.tvVictoryMessage.text = messageText

                    if (state.nextLessonId != null && state.nextLessonTitle != null) {
                        binding.btnNextLesson.isEnabled = true
                        binding.btnNextLesson.text = getString(R.string.victory_next_lesson)
                    } else {
                        binding.btnNextLesson.isEnabled = false
                        binding.btnNextLesson.text = getString(R.string.victory_no_next_lesson)
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is VictoryHallEvent.LessonReset -> {
                            LessonProgressPreferences.setCurrentLesson(
                                requireContext(),
                                event.lessonId,
                                event.lessonTitle
                            )
                            val directions = VictoryHallFragmentDirections
                                .actionVictoryHallFragmentToLessonDetailFragment(event.lessonId)
                            findNavController().navigate(directions)
                        }
                    }
                }
            }
        }
    }
}

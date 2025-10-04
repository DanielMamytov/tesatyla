package sr.otaryp.tesatyla.presentation.ui.focus

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.Locale
import java.util.concurrent.TimeUnit
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.FocusPreferences
import sr.otaryp.tesatyla.databinding.FragmentFocusBinding
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient

class FocusFragment : Fragment() {

    private var _binding: FragmentFocusBinding? = null
    private val binding get() = _binding!!

    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var isFocusSession = true
    private var remainingMillis = FOCUS_DURATION_MILLIS

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFocusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupListeners()
        setupBackNavigation()

        binding.timerTv.applyVerticalGradient()
        binding.titleTv.applyVerticalGradient()
        binding.completedPomodoros.applyVerticalGradient()
        binding.CompletedTv.applyVerticalGradient()
    }

    override fun onDestroyView() {
        binding.circularProgressBar.stopIndeterminateAnimation(resetToStart = true)
        countDownTimer?.cancel()
        countDownTimer = null
        _binding = null
        super.onDestroyView()
    }

    private fun setupUi() {

        updateCompletedPomodoros()
        updateSessionLabels()
        updateTimerUi()
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener { startTimer() }
        binding.btnPause.setOnClickListener { pauseTimer() }
        binding.btnReplay.setOnClickListener { resetTimer() }
    }

    private fun setupBackNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun startTimer() {
        if (isTimerRunning) return

        countDownTimer = object : CountDownTimer(remainingMillis, TICK_INTERVAL_MILLIS) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMillis = millisUntilFinished
                updateTimerUi()
            }

            override fun onFinish() {
                remainingMillis = 0L
                updateTimerUi()
                handleTimerFinished()
            }
        }.also { it.start() }

        isTimerRunning = true
        binding.circularProgressBar.startIndeterminateAnimation()
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
        binding.circularProgressBar.stopIndeterminateAnimation()
    }

    private fun resetTimer() {
        pauseTimer()
        isFocusSession = true
        remainingMillis = FOCUS_DURATION_MILLIS
        updateSessionLabels()
        updateTimerUi()
        binding.circularProgressBar.stopIndeterminateAnimation(resetToStart = true)
    }

    private fun handleTimerFinished() {
        isTimerRunning = false
        if (isFocusSession) {
            Toast.makeText(requireContext(), getString(R.string.focus_victory_message), Toast.LENGTH_SHORT).show()
            val updatedCount = FocusPreferences.incrementTodayCount(requireContext())
            binding.completedPomodoros.text = updatedCount.toString()
            isFocusSession = false
            remainingMillis = BREAK_DURATION_MILLIS
            updateSessionLabels()
            updateTimerUi()
            Toast.makeText(
                requireContext(),
                getString(R.string.focus_break_message, BREAK_DURATION_MINUTES),
                Toast.LENGTH_SHORT,
            ).show()
            startTimer()
        } else {
            Toast.makeText(requireContext(), getString(R.string.focus_break_complete_message), Toast.LENGTH_SHORT).show()
            isFocusSession = true
            remainingMillis = FOCUS_DURATION_MILLIS
            updateSessionLabels()
            updateTimerUi()
            binding.circularProgressBar.stopIndeterminateAnimation()
        }
    }

    private fun updateSessionLabels() {
        val sessionLabelRes = if (isFocusSession) {
            R.string.focus_session_label
        } else {
            R.string.break_session_label
        }
//        binding.sessionStatus.setText(sessionLabelRes)
//        binding.sessionInfo.text = getString(
//            R.string.focus_session_info,
//            FOCUS_DURATION_MINUTES,
//            BREAK_DURATION_MINUTES,
//        )
    }

    private fun updateCompletedPomodoros() {
        val count = FocusPreferences.ensureTodayCount(requireContext())
        binding.completedPomodoros.text = count.toString()
    }

    private fun updateTimerUi() {
        binding.timerTv.text = formatTime(remainingMillis)
        val duration = if (isFocusSession) FOCUS_DURATION_MILLIS else BREAK_DURATION_MILLIS
        val progress = if (duration == 0L) 0 else {
            val elapsed = duration - remainingMillis
            ((elapsed.coerceAtLeast(0L).toFloat() / duration) * PROGRESS_MAX)
                .toInt()
                .coerceIn(0, PROGRESS_MAX)
        }
        binding.circularProgressBar.setProgress(progress.toFloat())
    }


    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % SECONDS_IN_MINUTE
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    companion object {
        private const val FOCUS_DURATION_MINUTES = 25
        private const val BREAK_DURATION_MINUTES = 5
        private const val SECONDS_IN_MINUTE = 60
        private const val TICK_INTERVAL_MILLIS = 1_000L

        private val FOCUS_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(FOCUS_DURATION_MINUTES.toLong())
        private val BREAK_DURATION_MILLIS = TimeUnit.MINUTES.toMillis(BREAK_DURATION_MINUTES.toLong())
    }
}

package sr.otaryp.tesatyla.presentation.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.FocusPreferences
import sr.otaryp.tesatyla.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

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
        binding.progressBar.max = PROGRESS_MAX
        updateProgress()
    }

    override fun onResume() {
        super.onResume()
        updateProgress()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateProgress() {
        val completedToday = FocusPreferences.ensureTodayCount(requireContext())
        val percent = ((completedToday.toFloat() / DAILY_GOAL) * PROGRESS_MAX).toInt().coerceIn(0, PROGRESS_MAX)

        binding.progressPercentage.text = getString(R.string.progress_percentage_format, percent)
        binding.pomodoroCycles.text = getString(R.string.progress_cycles_format, completedToday)
        binding.progressBar.progress = percent
    }

    companion object {
        private const val DAILY_GOAL = 12f
        private const val PROGRESS_MAX = 100
    }
}

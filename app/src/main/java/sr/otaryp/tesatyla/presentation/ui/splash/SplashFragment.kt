package sr.otaryp.tesatyla.presentation.ui.splash

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LaunchPreferences
import sr.otaryp.tesatyla.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private var progressAnimator: ValueAnimator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        startProgressAnimation()
        launchNavigation()
    }

    override fun onDestroyView() {
        progressAnimator?.cancel()
        progressAnimator = null
        _binding = null
        super.onDestroyView()
    }

    private fun hideBottomNavigation() {
        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation?.visibility = View.GONE
    }

    private fun startProgressAnimation() {
        binding.progressBar.progress = 0
        progressAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration = PROGRESS_ANIMATION_DURATION
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                binding.progressBar.progress = animator.animatedValue as Int
            }
            start()
        }
    }

    private fun launchNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DURATION)
            val destination = if (LaunchPreferences.isOnboardingComplete(requireContext())) {
                R.id.nav_home
            } else {
                R.id.onBoardingFragment
            }
            val options = navOptions {
                popUpTo(R.id.splashFragment) {
                    inclusive = true
                }
            }

            findNavController().navigate(destination, null, options)
        }
    }

    companion object {
        private const val SPLASH_DURATION = 1500L
        private const val PROGRESS_ANIMATION_DURATION = 900L
    }
}

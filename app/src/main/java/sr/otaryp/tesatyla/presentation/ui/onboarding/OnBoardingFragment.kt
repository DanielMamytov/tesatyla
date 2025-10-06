package sr.otaryp.tesatyla.presentation.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LaunchPreferences
import sr.otaryp.tesatyla.databinding.FragmentOnBoardingBinding
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var tabMediator: TabLayoutMediator? = null

    private val slides by lazy {
        listOf(
            OnboardingSlide(
                titleRes = R.string.onboarding_daily_learning_title,
                descriptionRes = R.string.onboarding_daily_learning_description
            ),
            OnboardingSlide(
                titleRes = R.string.onboarding_quick_tips_title,
                descriptionRes = R.string.onboarding_quick_tips_description
            ),
            OnboardingSlide(
                titleRes = R.string.onboarding_progress_title,
                descriptionRes = R.string.onboarding_progress_description

            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,

        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPagerOnboarding.adapter = OnboardingPagerAdapter(slides)
        binding.tabDotsOnboarding.tabIconTint = null
        tabMediator = TabLayoutMediator(binding.tabDotsOnboarding, binding.viewPagerOnboarding) { tab, _ ->
            tab.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.onboarding_dot)
        }.also { mediator ->
            mediator.attach()
        }
        binding.tabDotsOnboarding.post { applyDotsSpacing() }

        updateEnterButtonVisibility(0)

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateEnterButtonVisibility(position)

            }
        }.also { callback ->
            binding.viewPagerOnboarding.registerOnPageChangeCallback(callback)
        }

        binding.btnEnterKingdom.setOnClickListener {
            LaunchPreferences.setOnboardingComplete(requireContext())
            val options = navOptions {
                popUpTo(R.id.onBoardingFragment) {
                    inclusive = true
                }
            }
            findNavController().navigate(R.id.nav_home, null, options)
        }

        binding.btnEnterKingdom.applyVerticalGradient()
    }


    private fun updateEnterButtonVisibility(position: Int) {
        binding.btnEnterKingdom.isVisible = position == slides.lastIndex
    }

    private fun applyDotsSpacing() {
        val tabStrip = binding.tabDotsOnboarding.getChildAt(0) as? ViewGroup ?: return
        val margin = resources.getDimensionPixelOffset(R.dimen.onboarding_indicator_margin)
        for (index in 0 until tabStrip.childCount) {
            val tabView = tabStrip.getChildAt(index)
            val params = tabView.layoutParams as? ViewGroup.MarginLayoutParams ?: continue
            params.marginStart = margin
            params.marginEnd = margin
            tabView.layoutParams = params
        }
    }

    override fun onDestroyView() {
        pageChangeCallback?.let { binding.viewPagerOnboarding.unregisterOnPageChangeCallback(it) }
        binding.viewPagerOnboarding.adapter = null
        tabMediator?.detach()
        pageChangeCallback = null
        tabMediator = null
        _binding = null
        super.onDestroyView()
    }
}

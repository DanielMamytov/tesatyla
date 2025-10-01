package sr.otaryp.tesatyla.presentation.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.preferences.LaunchPreferences
import sr.otaryp.tesatyla.databinding.FragmentOnBoardingBinding
import kotlin.math.abs

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private val slides by lazy {
        listOf(
            OnboardingSlide(
                titleRes = R.string.onboarding_daily_learning_title,
                descriptionRes = R.string.onboarding_daily_learning_description,
                illustrationRes = R.drawable.ic_onboarding_daily_learning
            ),
            OnboardingSlide(
                titleRes = R.string.onboarding_quick_tips_title,
                descriptionRes = R.string.onboarding_quick_tips_description,
                illustrationRes = R.drawable.ic_onboarding_quick_tips
            ),
            OnboardingSlide(
                titleRes = R.string.onboarding_progress_title,
                descriptionRes = R.string.onboarding_progress_description,
                illustrationRes = R.drawable.ic_onboarding_progress
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupIndicators()

        updateEnterButtonVisibility(0)

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateEnterButtonVisibility(position)
                updateIndicators(position)

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
    }

    private fun setupViewPager() {
        val sidePadding = resources.getDimensionPixelSize(R.dimen.onboarding_viewpager_side_padding)
        val pageMargin = resources.getDimensionPixelSize(R.dimen.onboarding_viewpager_page_margin)

        binding.viewPagerOnboarding.apply {
            adapter = OnboardingPagerAdapter(slides)
            offscreenPageLimit = slides.size
            clipToPadding = false
            clipChildren = false
            setPadding(sidePadding, 0, sidePadding, 0)
            (getChildAt(0) as? RecyclerView)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val transformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(pageMargin))
                addTransformer { page, position ->
                    val ratio = 1 - abs(position)
                    val scale = 0.9f + (ratio * 0.1f)
                    page.scaleY = scale
                    page.scaleX = scale
                    page.alpha = 0.6f + (ratio * 0.4f)
                }
            }

            setPageTransformer(transformer)
        }
    }

    private fun setupIndicators() {
        val indicatorSize = resources.getDimensionPixelSize(R.dimen.onboarding_indicator_size)
        val indicatorMargin = resources.getDimensionPixelSize(R.dimen.onboarding_indicator_margin)

        binding.layoutIndicators.removeAllViews()
        repeat(slides.size) {
            val indicator = AppCompatImageView(requireContext()).apply {
                setImageResource(R.drawable.bg_onboarding_indicator_inactive)
                layoutParams = LinearLayout.LayoutParams(indicatorSize, indicatorSize).apply {
                    setMargins(indicatorMargin, 0, indicatorMargin, 0)
                }
            }
            binding.layoutIndicators.addView(indicator)
        }

        updateIndicators(0)
    }

    private fun updateIndicators(position: Int) {
        val indicatorCount = binding.layoutIndicators.childCount
        for (index in 0 until indicatorCount) {
            val indicator = binding.layoutIndicators.getChildAt(index) as? AppCompatImageView
            indicator?.setImageResource(
                if (index == position) R.drawable.bg_onboarding_indicator_active
                else R.drawable.bg_onboarding_indicator_inactive
            )
        }
    }


    private fun updateEnterButtonVisibility(position: Int) {
        binding.btnEnterKingdom.isVisible = position == slides.lastIndex
    }

    override fun onDestroyView() {
        pageChangeCallback?.let { binding.viewPagerOnboarding.unregisterOnPageChangeCallback(it) }
        binding.viewPagerOnboarding.adapter = null
        binding.layoutIndicators.removeAllViews()

        pageChangeCallback = null
        _binding = null
        super.onDestroyView()
    }
}

package sr.otaryp.tesatyla.presentation.ui.main

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.content.InspirationRepository
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.DialogSetRoyalQuestBinding
import sr.otaryp.tesatyla.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderContinueLessonState()
        setupClickListeners()
    }

    private fun renderContinueLessonState() {
        val lessonProgress = LessonProgressPreferences.getCurrentLesson(requireContext())
        val messageRes = if (lessonProgress != null) {
            R.string.home_continue_card_in_progress
        } else {
            R.string.home_continue_card_start
        }
        binding.textContinueLessonMessage.setText(messageRes)
    }

    private fun setupClickListeners() {
        binding.continueLesson.setOnClickListener {
            val navController = findNavController()
            val lessonProgress = LessonProgressPreferences.getCurrentLesson(requireContext())
            if (lessonProgress != null) {
                navController.navigate(R.id.action_nav_home_to_lessonDetailFragment)
            } else {
                navController.navigate(R.id.action_nav_home_to_nav_lessons)
            }
        }

        binding.randomArticle.setOnClickListener {
            val randomArticle = InspirationRepository.getRandomArticle()
            val directions = HomeFragmentDirections.actionNavHomeToArticleDetailFragment(
                articleTitle = randomArticle.title,
                articleContent = randomArticle.content
            )
            findNavController().navigate(directions)
        }

        binding.dailyTip.setOnClickListener {
            showDailyTipDialog()
        }
    }

    private fun showDailyTipDialog() {
        val dialogBinding = DialogSetRoyalQuestBinding.inflate(layoutInflater)
        val tips = InspirationRepository.dailyTips
        var currentIndex = InspirationRepository.getTipIndexForToday()

        fun renderTip() {
            val positionText = getString(
                R.string.dialog_tip_position,
                currentIndex + 1,
                tips.size
            )
            dialogBinding.textTipContent.text = tips[currentIndex]
            dialogBinding.textTipPosition.text = positionText
        }

        renderTip()

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss() }
        dialogBinding.buttonNext.setOnClickListener {
            currentIndex = (currentIndex + 1) % tips.size
            renderTip()
        }

        val gestureDetector = GestureDetectorCompat(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            private val swipeThreshold = 100
            private val swipeVelocityThreshold = 100

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 == null || e2 == null) return false
                val diffX = e2.x - e1.x
                val diffY = e2.y - e1.y
                if (kotlin.math.abs(diffX) > kotlin.math.abs(diffY)) {
                    if (kotlin.math.abs(diffX) > swipeThreshold && kotlin.math.abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            currentIndex = if (currentIndex - 1 < 0) tips.lastIndex else currentIndex - 1
                        } else {
                            currentIndex = (currentIndex + 1) % tips.size
                        }
                        renderTip()
                        return true
                    }
                }
                return false
            }
        })

        dialogBinding.tipContainer.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }

        dialog.show()
    }
}

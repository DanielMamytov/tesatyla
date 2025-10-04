package sr.otaryp.tesatyla.presentation.ui.main

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sr.otaryp.tesatyla.presentation.ui.main.HomeFragmentDirections
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
        applyVerticalGradient(binding.textWelcome)
        applyVerticalGradient(binding.textWelcome1)
        applyVerticalGradient(binding.continueLessonTv)
        applyVerticalGradient(binding.discoverInspirationTv)
        applyVerticalGradient(binding.dailyTipTv)
        applyVerticalGradient(binding.randomArticleTv)
    }

    private fun applyVerticalGradient(tv: TextView) {
        tv.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                tv.removeOnLayoutChangeListener(this)
                val h = tv.height.coerceAtLeast(tv.lineHeight) // на случай wrap_content до измерения
                val shader = LinearGradient(
                    0f, 0f, 0f, h.toFloat(),                 // вертикальный градиент сверху вниз
                    intArrayOf(Color.parseColor("#FBF990"),  // light
                        Color.parseColor("#F8BB24")), // dark
                    null,
                    Shader.TileMode.CLAMP
                )
                tv.paint.shader = shader
                tv.invalidate()
            }
        })
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
        binding.imageSettings.setOnClickListener {
            findNavController().navigate(R.id.action_global_settingsFragment)
        }

        binding.continueLesson.setOnClickListener {
            val navController = findNavController()
            val lessonProgress = LessonProgressPreferences.getCurrentLesson(requireContext())
            if (lessonProgress != null) {
                val directions = HomeFragmentDirections
                    .actionNavHomeToLessonDetailFragment(lessonProgress.lessonId)
                navController.navigate(directions)
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
        var currentIndex = 0

        fun renderTip() {
            dialogBinding.tipText.text = tips[currentIndex] // меняем текст совета
        }

        renderTip() // показываем первый совет

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Кнопка "Got it" закрывает окно
        dialogBinding.buttonClose.setOnClickListener { dialog.dismiss() }

        // Кнопка "Next tip" → переключаемся на следующий совет
        dialogBinding.buttonNext.setOnClickListener {
            currentIndex = (currentIndex + 1) % tips.size
            renderTip()
        }

        dialog.show()
    }

}

package sr.otaryp.tesatyla.presentation.ui.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sr.otaryp.tesatyla.R
import sr.otaryp.tesatyla.data.lessons.LessonDatabase
import sr.otaryp.tesatyla.data.preferences.FocusPreferences
import sr.otaryp.tesatyla.data.preferences.LaunchPreferences
import sr.otaryp.tesatyla.data.preferences.LessonProgressPreferences
import sr.otaryp.tesatyla.databinding.FragmentSettingsBinding
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        binding.mainTv.applyVerticalGradient()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.buttonShareApp.setOnClickListener { shareApp() }
        binding.btnRateUs.setOnClickListener { openStorePage() }
        binding.buttonPrivacyPolicy.setOnClickListener { openPrivacyPolicy() }
        binding.buttonTerms.setOnClickListener { openTermsAndConditions() }
        binding.buttonClearData.setOnClickListener { confirmClearAppData() }
    }

    private fun shareApp() {
        val packageName = requireContext().packageName
        val shareMessage = getString(R.string.settings_share_message, packageName)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.settings_share_chooser_title)))
    }

    private fun openStorePage() {
        val packageName = requireContext().packageName
        val playStoreUri = Uri.parse("market://details?id=$packageName")
        val marketIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        try {
            startActivity(marketIntent)
        } catch (error: ActivityNotFoundException) {
            val webUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    }

    private fun openPrivacyPolicy() {
        findNavController().navigate(R.id.action_settingsFragment_to_privacyPolicyFragment)
    }

    private fun openTermsAndConditions() {
        findNavController().navigate(R.id.action_settingsFragment_to_termsConditionsFragment)
    }

    private fun confirmClearAppData() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.settings_clear_data_confirm_title)
            .setMessage(R.string.settings_clear_data_confirm_message)
            .setPositiveButton(R.string.settings_clear_data_confirm_positive) { dialog, _ ->
                dialog.dismiss()
                clearAppData()
            }
            .setNegativeButton(R.string.settings_clear_data_confirm_negative, null)
            .show()
    }

    private fun clearAppData() {
        val appContext = requireContext().applicationContext
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                LessonDatabase.getInstance(appContext).clearAllTables()
                clearPreferences(appContext)
            }
            Toast.makeText(requireContext(), R.string.settings_clear_data_success, Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearPreferences(context: Context) {
        LaunchPreferences.clear(context)
        FocusPreferences.clear(context)
        LessonProgressPreferences.clear(context)
    }
}

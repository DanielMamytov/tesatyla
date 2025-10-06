package sr.otaryp.tesatyla

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import sr.otaryp.tesatyla.presentation.ui.lessons.applyVerticalGradient
import sr.otaryp.tesatyla.presentation.ui.setupCustomBottomNav
import sr.otaryp.tesatyla.presentation.ui.setSelectedIndex

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val destinationsWithBottomNav = setOf(
        R.id.nav_home,
        R.id.nav_lessons,
        R.id.nav_articles,
        R.id.nav_progress,
        R.id.nav_focus
    )
    private val destinationToNavIndex = mapOf(
        R.id.nav_home to 0,
        R.id.nav_lessons to 1,
        R.id.nav_articles to 2,
        R.id.nav_progress to 3,
        R.id.nav_focus to 4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val text = findViewById<TextView>(R.id.label)
        text.applyVerticalGradient()
        
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        onBackPressedDispatcher.addCallback(this) {
            handleOnBackPressed()
        }

        val bottomNav = findViewById<View>(R.id.bottomBar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav?.isVisible = destination.id in destinationsWithBottomNav
            destinationToNavIndex[destination.id]?.let { index ->
                bottomNav?.setSelectedIndex(index)
            }
        }

        bottomNav?.let { view ->
            view.setupCustomBottomNav { index ->
                when (index) {
                    0 -> openHome()
                    1 -> openLessons()
                    2 -> openArticles()
                    3 -> openProgress()
                    4 -> openFocus()
                }
            }
        }
    }

    private fun openHome() = navigateToRootDestination(R.id.nav_home)

    private fun openLessons() = navigateToRootDestination(R.id.nav_lessons)

    private fun openArticles() = navigateToRootDestination(R.id.nav_articles)

    private fun openProgress() = navigateToRootDestination(R.id.nav_progress)

    private fun openFocus() = navigateToRootDestination(R.id.nav_focus)

    private fun navigateToRootDestination(destinationId: Int) {
        if (!::navController.isInitialized) return
        if (navController.currentDestination?.id == destinationId) return

        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(navController.currentDestination?.id ?: destinationId, true)
            .build()

        navController.navigate(destinationId, null, navOptions)
    }

    private fun handleOnBackPressed() {
        if (!::navController.isInitialized) {
            finish()
            return
        }

        if (navController.popBackStack()) {
            return
        }

        val currentDestinationId = navController.currentDestination?.id ?: run {
            finish()
            return
        }

        if (currentDestinationId in destinationsWithBottomNav && currentDestinationId != R.id.nav_home) {
            if (!navController.popBackStack(R.id.nav_home, false)) {
                val navOptions = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(currentDestinationId, true)
                    .build()
                navController.navigate(R.id.nav_home, null, navOptions)
            }
            return
        }

        finish()
    }
}

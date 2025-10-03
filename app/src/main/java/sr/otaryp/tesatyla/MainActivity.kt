package sr.otaryp.tesatyla

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import sr.otaryp.tesatyla.presentation.ui.setupCustomBottomNav

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigation.setupWithNavController(navController)

        val destinationsWithBottomNav = setOf(
            R.id.nav_home,
            R.id.nav_lessons,
            R.id.nav_articles,
            R.id.nav_progress,
            R.id.nav_focus
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.isVisible = destination.id in destinationsWithBottomNav
        }

        val bottomNav = findViewById<View>(R.id.customBottomNav)
        bottomNav.setupCustomBottomNav { index ->
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
}

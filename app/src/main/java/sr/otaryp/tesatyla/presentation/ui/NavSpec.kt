package sr.otaryp.tesatyla.presentation.ui

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import sr.otaryp.tesatyla.R

data class NavSpec(val viewId: Int, val iconRes: Int, val label: String)

private val items = listOf(
    NavSpec(R.id.itemHome,     R.drawable.ic_home,     "Home"),
    NavSpec(R.id.itemLessons,  R.drawable.ic_lessons,  "Lessons"),
    NavSpec(R.id.itemArticles, R.drawable.ic_articles, "Articles"),
    NavSpec(R.id.itemProgress, R.drawable.ic_progress, "Progress"),
    NavSpec(R.id.itemFocus,    R.drawable.ic_focus,    "Focus"),
)

fun View.setupCustomBottomNav(onSelect: (index: Int) -> Unit) {
    items.forEachIndexed { index, spec ->
        val item = findViewById<LinearLayout>(spec.viewId)
        val icon = item.findViewById<ImageView>(R.id.icon)
        val label = item.findViewById<TextView>(R.id.label)

        icon.setImageResource(spec.iconRes)
        label.text = spec.label

        item.setOnClickListener {
            setSelectedIndex(index)
            onSelect(index)
        }
    }
    setSelectedIndex(0) // по умолчанию выбрано "Home"
}

fun View.setSelectedIndex(index: Int) {
    items.forEachIndexed { i, spec ->
        val item = findViewById<LinearLayout>(spec.viewId)
        item.isSelected = (i == index)
    }
}

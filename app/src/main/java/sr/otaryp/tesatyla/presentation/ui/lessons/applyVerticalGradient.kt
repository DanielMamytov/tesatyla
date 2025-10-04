package sr.otaryp.tesatyla.presentation.ui.lessons

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.widget.TextView

fun TextView.applyVerticalGradient(startColor: String = "#FBF990", endColor: String = "#F8BB24") {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View, left: Int, top: Int, right: Int, bottom: Int,
            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            removeOnLayoutChangeListener(this)
            val h = height.coerceAtLeast(lineHeight)
            val shader = LinearGradient(
                0f, 0f, 0f, h.toFloat(),
                intArrayOf(Color.parseColor(startColor), Color.parseColor(endColor)),
                null,
                Shader.TileMode.CLAMP
            )
            paint.shader = shader
            invalidate()
        }
    })
}

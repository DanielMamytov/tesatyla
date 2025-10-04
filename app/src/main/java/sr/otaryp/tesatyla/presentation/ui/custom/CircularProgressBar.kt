package sr.otaryp.tesatyla.presentation.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint: Paint = Paint()
    private val progressPaint: Paint = Paint()
    private var progress: Float = 0f
    private val strokeWidth: Int = 20 // Set stroke width
    private var radius: Float = 0f
    private val maxProgress = 100f

    init {
        // Paint for background circle (inactive portion)
        backgroundPaint.apply {
            color = Color.parseColor("#E0E0E0")
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth.toFloat()
            isAntiAlias = true
        }

        // Paint for progress circle (active portion)
        progressPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth.toFloat()
            isAntiAlias = true
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (Math.min(w, h) / 2f) - strokeWidth // Set radius based on view size

        // Apply gradient to progress paint once we know the view size
        progressPaint.shader = SweepGradient(
            w / 2f,
            h / 2f,
            intArrayOf(Color.parseColor("#1A7F7F"), Color.parseColor("#009999")),
            floatArrayOf(0f, 1f)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background circle
        canvas.drawCircle(width / 2f, height / 2f, radius, backgroundPaint)

        // Draw progress circle (animated)
        val angle = (360 * progress) / maxProgress
        canvas.drawArc(
            strokeWidth.toFloat(), strokeWidth.toFloat(),
            (width - strokeWidth).toFloat(), (height - strokeWidth).toFloat(),
            -90f, angle, false, progressPaint
        ) // -90 to start the arc from the top
    }

    // Method to update progress dynamically
    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, maxProgress)
        invalidate() // Redraw the view with updated progress
    }
}

package sr.otaryp.tesatyla.presentation.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val backgroundPaint: Paint = Paint()
    private val progressPaint: Paint = Paint()
    private var progress: Float = 0f // Start with 0% progress
    private val strokeWidth: Int = 20 // Set stroke width
    private var radius: Float = 0f

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

        // Apply gradient to progress paint
        val gradient = RadialGradient(0f, 0f, radius, Color.parseColor("#1A7F7F"), Color.parseColor("#009999"), Shader.TileMode.CLAMP)
        progressPaint.shader = gradient

        // Animate progress (example)
        val animator = ValueAnimator.ofFloat(0f, 360f) // Animate from 0% to 100%
        animator.duration = 2000 // Duration of animation (2 seconds)
        animator.addUpdateListener {
            progress = it.animatedValue as Float
            invalidate() // Redraw view to show progress update
        }
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (Math.min(w, h) / 2f) - strokeWidth // Set radius based on view size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw background circle
        canvas.drawCircle(width / 2f, height / 2f, radius, backgroundPaint)

        // Draw progress circle (animated)
        val angle = (360 * progress) / 100
        canvas.drawArc(
            strokeWidth.toFloat(), strokeWidth.toFloat(),
            (width - strokeWidth).toFloat(), (height - strokeWidth).toFloat(),
            -90f, angle, false, progressPaint
        ) // -90 to start the arc from the top
    }

    // Method to update progress dynamically
    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate() // Redraw the view with updated progress
    }
}

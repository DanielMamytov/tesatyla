package sr.otaryp.tesatyla.presentation.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progress: Float = 0f
    private val strokeWidth: Float = resources.displayMetrics.density * 16f
    private var radius: Float = 0f
    private val maxProgress = 100f

    private var currentStartAngle = -90f
    private var staticStartAngle: Float? = null
    private val spinSweepAngle = 270f
    private var isIndeterminate = false
    private val gradientMatrix = Matrix()
    private var progressShader: SweepGradient? = null

    private val rotationAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = 1_500L
        interpolator = LinearInterpolator()
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animator ->
            currentStartAngle = (animator.animatedValue as Float) - 90f
            staticStartAngle = currentStartAngle
            invalidate()
        }
    }

    init {
        backgroundPaint.apply {
            color = Color.TRANSPARENT
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth
        }

        progressPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (minOf(w, h) / 2f) - (strokeWidth / 2f)

        progressShader = SweepGradient(
            w / 2f,
            h / 2f,
            intArrayOf(
                Color.parseColor("#1DF2F8"),
                Color.parseColor("#00F8FF"),
                Color.parseColor("#001DF2F8"),
            ),
            floatArrayOf(0f, 0.72f, 1f),
        ).also(progressPaint::setShader)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (backgroundPaint.color != Color.TRANSPARENT) {
            canvas.drawCircle(width / 2f, height / 2f, radius, backgroundPaint)
        }

        val startAngle = when {
            isIndeterminate -> currentStartAngle
            staticStartAngle != null -> staticStartAngle!!
            else -> -90f
        }

        val sweepAngle = when {
            isIndeterminate || staticStartAngle != null -> spinSweepAngle
            else -> (360f * progress) / maxProgress
        }

        progressShader?.let { shader ->
            gradientMatrix.reset()
            gradientMatrix.postRotate(startAngle + 90f, width / 2f, height / 2f)
            shader.setLocalMatrix(gradientMatrix)
        }

        canvas.drawArc(
            strokeWidth / 2f,
            strokeWidth / 2f,
            width - (strokeWidth / 2f),
            height - (strokeWidth / 2f),
            startAngle,
            sweepAngle,
            false,
            progressPaint,
        )
    }

    fun setProgress(progress: Float) {
        this.progress = progress.coerceIn(0f, maxProgress)
        staticStartAngle = null
        invalidate()
    }

    fun startIndeterminateAnimation() {
        if (isIndeterminate) return
        isIndeterminate = true
        rotationAnimator.start()
    }

    fun stopIndeterminateAnimation(resetToStart: Boolean = false) {
        if (!isIndeterminate && !rotationAnimator.isRunning) {
            if (resetToStart) {
                staticStartAngle = null
                currentStartAngle = -90f
                invalidate()
            }
            return
        }

        rotationAnimator.cancel()
        isIndeterminate = false

        if (resetToStart) {
            staticStartAngle = null
            currentStartAngle = -90f
        }

        invalidate()
    }

    override fun onDetachedFromWindow() {
        rotationAnimator.cancel()
        super.onDetachedFromWindow()
    }
}

package sr.otaryp.tesatyla.presentation.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import sr.otaryp.tesatyla.R
import kotlin.math.min

class CircularProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var max = 100
    private var progress = 0

    private var progressColor = Color.WHITE
    private var trackColor = 0x66FFFFFF // полупрозрачный белый
    private var strokeWidthPx = dp(12f)
    private var headFade = true

    private val rect = RectF()
    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar)
            progressColor = ta.getColor(
                R.styleable.CircularProgressBar_cpb_progressColor,
                Color.WHITE
            )
            trackColor = ta.getColor(
                R.styleable.CircularProgressBar_cpb_trackColor,
                0x66FFFFFF
            )
            strokeWidthPx = ta.getDimension(
                R.styleable.CircularProgressBar_cpb_strokeWidth,
                dp(12f)
            )
            headFade = ta.getBoolean(R.styleable.CircularProgressBar_cpb_headFade, true)
            ta.recycle()
        }
        trackPaint.color = trackColor
        trackPaint.strokeWidth = strokeWidthPx

        progressPaint.color = progressColor
        progressPaint.strokeWidth = strokeWidthPx
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val size = min(w, h).toFloat()
        val pad = strokeWidthPx / 2f + dp(2f)
        rect.set(pad, pad, size - pad, size - pad)

        if (headFade) {
            // лёгкая прозрачность к концу дуги
            val shader = SweepGradient(
                width / 2f, height / 2f,
                intArrayOf(progressColor, progressColor, progressColor and 0x00FFFFFF),
                floatArrayOf(0f, 0.9f, 1f)
            )
            progressPaint.shader = shader
        } else {
            progressPaint.shader = null
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // фон-трек (полный круг)
        canvas.drawArc(rect, -90f, 360f, false, trackPaint)
        // прогресс
        val sweep = 360f * (progress.toFloat() / max.coerceAtLeast(1))
        canvas.drawArc(rect, -90f, sweep, false, progressPaint)
    }

    fun setProgress(value: Int) {
        progress = value.coerceIn(0, max)
        invalidate()
    }

    fun setMax(m: Int) {
        max = m.coerceAtLeast(1)
        invalidate()
    }

    fun setProgressColor(color: Int) {
        progressColor = color
        progressPaint.color = color
        progressPaint.shader = null // пересоздастся в onSizeChanged при следующем вызове
        invalidate()
    }

    fun setTrackColor(color: Int) {
        trackColor = color
        trackPaint.color = color
        invalidate()
    }

    fun setStrokeWidth(px: Float) {
        strokeWidthPx = px
        trackPaint.strokeWidth = px
        progressPaint.strokeWidth = px
        requestLayout()
        invalidate()
    }

    private fun dp(v: Float) = v * resources.displayMetrics.density
}

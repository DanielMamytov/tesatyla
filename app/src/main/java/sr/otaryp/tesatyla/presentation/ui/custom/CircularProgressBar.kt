package sr.otaryp.tesatyla.presentation.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View

class CircularProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var progress = 0f // 0f..1f

    fun setProgress(p: Float) {
        progress = p.coerceIn(0f, 1f)
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2 * 0.8f
        val centerX = width / 2
        val centerY = height / 2

        // базовый круг (фон)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        paint.color = Color.DKGRAY
        canvas.drawCircle(centerX, centerY, radius, paint)

        // градиент прогресса с прозрачностью на конце
        val shader = SweepGradient(
            centerX, centerY,
            intArrayOf(Color.CYAN, Color.CYAN, Color.TRANSPARENT),
            floatArrayOf(0f, progress, 1f)
        )
        val matrix = Matrix()
        matrix.postRotate(-90f, centerX, centerY) // начинаем сверху
        shader.setLocalMatrix(matrix)

        paint.shader = shader
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            -90f,
            360f * progress,
            false,
            paint
        )

        paint.shader = null
    }
}

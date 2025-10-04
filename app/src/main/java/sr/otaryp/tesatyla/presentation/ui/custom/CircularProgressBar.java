package sr.otaryp.tesatyla.presentation.ui.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressBar extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private float progress = 0f; // Start with 0% progress
    private int strokeWidth = 20; // Set stroke width
    private float radius;
    
    // Constructor
    public CircularProgressBar(Context context) {
        super(context);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Paint for background circle (inactive portion)
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#E0E0E0"));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setAntiAlias(true);

        // Paint for progress circle (active portion)
        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setAntiAlias(true);

        // Apply gradient to progress paint
        RadialGradient gradient = new RadialGradient(0, 0, radius, Color.parseColor("#1A7F7F"), Color.parseColor("#009999"), Shader.TileMode.CLAMP);
        progressPaint.setShader(gradient);

        // Animate progress (example)
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360); // Animate from 0% to 100%
        animator.setDuration(2000); // Duration of animation (2 seconds)
        animator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate(); // Redraw view to show progress update
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2f - strokeWidth; // Set radius based on view size
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background circle
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, backgroundPaint);

        // Draw progress circle (animated)
        float angle = (360 * progress) / 100;
        canvas.drawArc(
                strokeWidth, strokeWidth, getWidth() - strokeWidth, getHeight() - strokeWidth, 
                -90, angle, false, progressPaint); // -90 to start the arc from the top
    }

    // Method to update progress dynamically
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate(); // Redraw the view with updated progress
    }
}

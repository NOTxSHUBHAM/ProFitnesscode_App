package com.example.profitness.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.example.profitness.R;

public class CircularProgressView extends View {
    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Paint subTextPaint;

    private RectF arcBounds;
    private float progress = 0f;
    private float maxProgress = 100f;
    private float strokeWidth = 20f;
    private int backgroundColor = Color.LTGRAY;
    private int progressColor = Color.BLUE;
    private int textColor = Color.BLACK;
    private String centerText = "0";
    private String subText = "Steps";

    private android.animation.ValueAnimator progressAnimator;

    public CircularProgressView(Context context) {
        super(context);
        init(null);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
            progressColor = ta.getColor(R.styleable.CircularProgressView_progressColor, progressColor);
            backgroundColor = ta.getColor(R.styleable.CircularProgressView_backgroundColor, backgroundColor);
            strokeWidth = ta.getDimension(R.styleable.CircularProgressView_strokeWidth, strokeWidth);
            ta.recycle();
        }

        setupPaints();
    }

    private void setupPaints() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(48f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subTextPaint.setColor(textColor);
        subTextPaint.setTextSize(16f);
        subTextPaint.setTextAlign(Paint.Align.CENTER);

        arcBounds = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = strokeWidth / 2;
        arcBounds.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background circle
        canvas.drawArc(arcBounds, 0, 360, false, backgroundPaint);

        // Draw progress arc
        float sweepAngle = (progress / maxProgress) * 360;
        canvas.drawArc(arcBounds, -90, sweepAngle, false, progressPaint);

        // Draw center text
        float centerY = getHeight() / 2;
        canvas.drawText(centerText, getWidth() / 2, centerY, textPaint);
        canvas.drawText(subText, getWidth() / 2, centerY + 30, subTextPaint);
    }

    public void setProgress(float progress, boolean animate) {
        if (animate) {
            animateProgress(progress);
        } else {
            this.progress = progress;
            invalidate();
        }
    }

    public void setCenterText(String text) {
        this.centerText = text;
        invalidate();
    }

    public void setSubText(String text) {
        this.subText = text;
        invalidate();
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public void setProgressColor(int color) {
        progressPaint.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(int color) {
        backgroundPaint.setColor(color);
        invalidate();
    }

    private void animateProgress(float targetProgress) {
        if (progressAnimator != null) {
            progressAnimator.cancel();
        }

        progressAnimator = android.animation.ValueAnimator.ofFloat(progress, targetProgress);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addUpdateListener(animation -> {
            progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        progressAnimator.start();
    }
}
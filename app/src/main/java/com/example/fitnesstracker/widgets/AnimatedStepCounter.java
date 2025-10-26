package com.example.profitness.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.example.profitness.R;

public class AnimatedStepCounter extends AppCompatTextView {

    private Animation stepAnimation;

    public AnimatedStepCounter(Context context) {
        super(context);
        init();
    }

    public AnimatedStepCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimatedStepCounter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        stepAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
    }

    public void setStepCountWithAnimation(int steps) {
        setText(String.valueOf(steps));
        startAnimation(stepAnimation);
    }

    public void setStepCount(int steps) {
        setText(String.valueOf(steps));
    }
}
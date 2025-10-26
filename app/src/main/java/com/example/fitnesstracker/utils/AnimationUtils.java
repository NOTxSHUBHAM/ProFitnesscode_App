package com.example.profitness.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import com.example.profitness.R;

public class AnimationUtils {

    public static void bounce(View view) {
        Animation bounce = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce);
        view.startAnimation(bounce);
    }

    private static Animation loadAnimation(Context context, int bounce) {
    }

    public static void slideUp(View view) {
        Animation slideUp = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_up);
        view.startAnimation(slideUp);
    }

    public static void fadeIn(View view) {
        Animation fadeIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.fade_in);
        view.startAnimation(fadeIn);
    }

    public static void pulse(View view) {
        Animation pulse = AnimationUtils.loadAnimation(view.getContext(), R.anim.pulse);
        view.startAnimation(pulse);
    }

    public static void scale(View view, float fromX, float toX, float fromY, float toY) {
        ScaleAnimation scale = new ScaleAnimation(
                fromX, toX, fromY, toY,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(300);
        scale.setFillAfter(true);
        view.startAnimation(scale);
    }
}
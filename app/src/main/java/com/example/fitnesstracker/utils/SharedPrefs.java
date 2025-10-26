package com.example.profitness.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static final String PREFS_NAME = "ProFitnessPrefs";
    private static SharedPrefs instance;
    private SharedPreferences prefs;

    private static final String KEY_FIRST_RUN = "first_run";
    private static final String KEY_DAILY_GOAL = "daily_goal";
    private static final String KEY_USER_WEIGHT = "user_weight";
    private static final String KEY_USER_HEIGHT = "user_height";
    private static final String KEY_USER_AGE = "user_age";
    private static final String KEY_USER_GENDER = "user_gender";

    private SharedPrefs(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefs getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefs(context);
        }
        return instance;
    }

    public boolean isFirstRun() {
        return prefs.getBoolean(KEY_FIRST_RUN, true);
    }

    public void setFirstRun(boolean firstRun) {
        prefs.edit().putBoolean(KEY_FIRST_RUN, firstRun).apply();
    }

    public int getDailyGoal() {
        return prefs.getInt(KEY_DAILY_GOAL, 10000);
    }

    public void setDailyGoal(int goal) {
        prefs.edit().putInt(KEY_DAILY_GOAL, goal).apply();
    }

    public double getUserWeight() {
        return prefs.getFloat(KEY_USER_WEIGHT, 70.0f);
    }

    public void setUserWeight(double weight) {
        prefs.edit().putFloat(KEY_USER_WEIGHT, (float) weight).apply();
    }

    public double getUserHeight() {
        return prefs.getFloat(KEY_USER_HEIGHT, 170.0f);
    }

    public void setUserHeight(double height) {
        prefs.edit().putFloat(KEY_USER_HEIGHT, (float) height).apply();
    }

    public int getUserAge() {
        return prefs.getInt(KEY_USER_AGE, 30);
    }

    public void setUserAge(int age) {
        prefs.edit().putInt(KEY_USER_AGE, age).apply();
    }

    public String getUserGender() {
        return prefs.getString(KEY_USER_GENDER, "male");
    }

    public void setUserGender(String gender) {
        prefs.edit().putString(KEY_USER_GENDER, gender).apply();
    }
}
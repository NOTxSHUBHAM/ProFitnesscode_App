package com.example.profitness.services;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class StepCounterService implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private Sensor accelerometerSensor;
    private StepUpdateListener stepUpdateListener;

    private int stepCount = 0;
    private int initialSteps = 0;
    private boolean isInitialized = false;
    private long lastUpdateTime = 0;
    private long activeTimeStart = 0;
    private boolean isActive = false;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface StepUpdateListener {
        void onStepCountUpdated(int steps, double distance, double calories, long activeTime);
        void onGoalAchieved();
        void onSensorError(String error);
    }

    public StepCounterService(Context context, StepUpdateListener listener) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.stepUpdateListener = listener;
        initializeSensors();
    }

    private void initializeSensors() {
        if (sensorManager != null) {
            stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            if (stepCounterSensor != null) {
                sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
            } else {
                if (stepUpdateListener != null) {
                    stepUpdateListener.onSensorError("Step counter not available");
                }
            }

            if (accelerometerSensor != null) {
                sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            handleStepCounter(event);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            handleAccelerometer(event);
        }
    }

    private void handleStepCounter(SensorEvent event) {
        if (!isInitialized) {
            initialSteps = (int) event.values[0];
            isInitialized = true;
            return;
        }

        int newStepCount = (int) event.values[0] - initialSteps;

        if (newStepCount > stepCount) {
            stepCount = newStepCount;
            updateActivityTracking();
            notifyStepUpdate();
        }
    }

    private void handleAccelerometer(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double magnitude = Math.sqrt(x*x + y*y + z*z);

        if (magnitude > 12.0) {
            if (!isActive) {
                isActive = true;
                activeTimeStart = System.currentTimeMillis();
            }
        } else {
            isActive = false;
        }
    }

    private void updateActivityTracking() {
        lastUpdateTime = System.currentTimeMillis();
    }

    private void notifyStepUpdate() {
        mainHandler.post(() -> {
            if (stepUpdateListener != null) {
                double distance = calculateDistance(stepCount);
                double calories = calculateCalories(stepCount);
                long activeTime = calculateActiveTime();

                stepUpdateListener.onStepCountUpdated(stepCount, distance, calories, activeTime);

                if (stepCount >= 10000 && stepCount % 10000 == 0) {
                    stepUpdateListener.onGoalAchieved();
                }
            }
        });
    }

    private double calculateDistance(int steps) {
        return steps * 0.762;
    }

    private double calculateCalories(int steps) {
        return steps * 0.04;
    }

    private long calculateActiveTime() {
        return isActive ? (System.currentTimeMillis() - activeTimeStart) / 1000 : 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes
    }

    public int getCurrentStepCount() {
        return stepCount;
    }

    public void resetStepCount() {
        if (isInitialized && sensorManager != null) {
            sensorManager.unregisterListener(this);
            isInitialized = false;
            stepCount = 0;
            initialSteps = 0;
            isActive = false;
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
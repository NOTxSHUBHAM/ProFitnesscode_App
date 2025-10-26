package com.example.profitness.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.profitness.R;
import com.example.profitness.database.AppDatabase;
import com.example.profitness.database.FitnessDao;
import com.example.profitness.database.entities.StepData;
import com.example.profitness.services.StepCounterService;
import com.example.profitness.utils.DateUtils;
import com.example.profitness.utils.SharedPrefs;
import com.example.profitness.widgets.CircularProgressView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardFragment extends Fragment implements StepCounterService.StepUpdateListener {

    private CircularProgressView progressView;
    private TextView tvDistance, tvCalories, tvActiveTime, tvGoalStatus;
    private Button btnReset;

    private StepCounterService stepCounterService;
    private FitnessDao fitnessDao;
    private SharedPrefs sharedPrefs;
    private int dailyGoal;
    private String currentDate;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        initializeViews(view);
        initializeDatabase();
        initializeSharedPrefs();
        initializeStepCounter();
        loadSavedData();

        return view;
    }

    private void initializeViews(View view) {
        progressView = view.findViewById(R.id.progress_view);
        tvDistance = view.findViewById(R.id.tv_distance);
        tvCalories = view.findViewById(R.id.tv_calories);
        tvActiveTime = view.findViewById(R.id.tv_active_time);
        tvGoalStatus = view.findViewById(R.id.tv_goal_status);
        btnReset = view.findViewById(R.id.btn_reset);

        dailyGoal = sharedPrefs.getDailyGoal();
        progressView.setMaxProgress(dailyGoal);

        btnReset.setOnClickListener(v -> resetStepCount());

        // Apply animations
        view.findViewById(R.id.stats_container).startAnimation(
                AnimationUtils.loadAnimation(getContext(), R.anim.slide_up)
        );
    }

    private void initializeDatabase() {
        AppDatabase database = AppDatabase.getDatabase(requireContext());
        fitnessDao = database.fitnessDao();
        currentDate = DateUtils.getCurrentDate();
    }

    private void initializeSharedPrefs() {
        sharedPrefs = SharedPrefs.getInstance(requireContext());
    }

    private void initializeStepCounter() {
        stepCounterService = new StepCounterService(requireContext(), this);
    }

    private void loadSavedData() {
        executorService.execute(() -> {
            StepData stepData = fitnessDao.getStepDataByDate(currentDate);
            if (stepData != null) {
                mainHandler.post(() -> {
                    updateUI(stepData.getStepCount(), stepData.getDistance(),
                            stepData.getCaloriesBurned(), stepData.getActiveTime());
                });
            }
        });
    }

    @Override
    public void onStepCountUpdated(int steps, double distance, double calories, long activeTime) {
        mainHandler.post(() -> {
            updateUI(steps, distance, calories, activeTime);
            saveStepData(steps, distance, calories, activeTime);
        });
    }

    private void updateUI(int steps, double distance, double calories, long activeTime) {
        progressView.setProgress(steps, true);
        progressView.setCenterText(String.valueOf(steps));

        tvDistance.setText(String.format("%.1f km", distance / 1000));
        tvCalories.setText(String.format("%.0f cal", calories));
        tvActiveTime.setText(DateUtils.formatDuration(activeTime));

        updateGoalStatus(steps);
    }

    private void updateGoalStatus(int steps) {
        int percentage = (int) ((steps / (float) dailyGoal) * 100);
        if (steps >= dailyGoal) {
            tvGoalStatus.setText(getString(R.string.goal_achieved));
            tvGoalStatus.setTextColor(getResources().getColor(R.color.success_green));
            tvGoalStatus.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        } else {
            tvGoalStatus.setText(String.format(getString(R.string.of_daily_goal), percentage));
            tvGoalStatus.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void saveStepData(int steps, double distance, double calories, long activeTime) {
        executorService.execute(() -> {
            StepData existingData = fitnessDao.getStepDataByDate(currentDate);

            if (existingData == null) {
                StepData newData = new StepData(currentDate, steps, distance, calories, activeTime,
                        DateUtils.getCurrentTimestamp());
                fitnessDao.insertStepData(newData);
            } else {
                existingData.setStepCount(steps);
                existingData.setDistance(distance);
                existingData.setCaloriesBurned(calories);
                existingData.setActiveTime(activeTime);
                existingData.setTimestamp(DateUtils.getCurrentTimestamp());

                if (steps >= dailyGoal) {
                    existingData.setGoalAchieved(1);
                }

                fitnessDao.updateStepData(existingData);
            }
        });
    }

    private void resetStepCount() {
        stepCounterService.resetStepCount();
        progressView.setProgress(0, true);
        progressView.setCenterText("0");
        tvDistance.setText("0.0 km");
        tvCalories.setText("0 cal");
        tvActiveTime.setText("00:00");
        tvGoalStatus.setText("0% of daily goal");

        executorService.execute(() -> {
            fitnessDao.deleteStepDataByDate(currentDate);
        });
    }

    @Override
    public void onGoalAchieved() {
        mainHandler.post(() -> {
            tvGoalStatus.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
            // Show celebration
            progressView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.pulse));
        });
    }

    @Override
    public void onSensorError(String error) {
        mainHandler.post(() -> {
            tvGoalStatus.setText(error);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (stepCounterService != null) {
            stepCounterService.stop();
        }
        executorService.shutdown();
    }
}
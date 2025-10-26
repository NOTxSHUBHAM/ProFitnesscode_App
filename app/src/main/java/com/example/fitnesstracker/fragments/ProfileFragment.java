package com.example.profitness.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.profitness.R;
import com.example.profitness.database.AppDatabase;
import com.example.profitness.database.FitnessDao;
import com.example.profitness.database.entities.UserGoals;
import com.example.profitness.utils.SharedPrefs;

public class ProfileFragment extends Fragment {

    private EditText etDailyGoal, etWeight, etHeight, etAge;
    private TextView tvTotalSteps, tvTotalCalories, tvBestDay;
    private Button btnSave;

    private FitnessDao fitnessDao;
    private SharedPrefs sharedPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(view);
        initializeDatabase();
        loadUserData();
        loadStats();

        return view;
    }

    private void initializeViews(View view) {
        etDailyGoal = view.findViewById(R.id.et_daily_goal);
        etWeight = view.findViewById(R.id.et_weight);
        etHeight = view.findViewById(R.id.et_height);
        etAge = view.findViewById(R.id.et_age);
        tvTotalSteps = view.findViewById(R.id.tv_total_steps);
        tvTotalCalories = view.findViewById(R.id.tv_total_calories);
        tvBestDay = view.findViewById(R.id.tv_best_day);
        btnSave = view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> saveUserData());
    }

    private void initializeDatabase() {
        AppDatabase database = AppDatabase.getDatabase(requireContext());
        fitnessDao = database.fitnessDao();
        sharedPrefs = SharedPrefs.getInstance(requireContext());
    }

    private void loadUserData() {
        etDailyGoal.setText(String.valueOf(sharedPrefs.getDailyGoal()));
        etWeight.setText(String.valueOf(sharedPrefs.getUserWeight()));
        etHeight.setText(String.valueOf(sharedPrefs.getUserHeight()));
        etAge.setText(String.valueOf(sharedPrefs.getUserAge()));
    }

    private void loadStats() {
        new Thread(() -> {
            int totalSteps = fitnessDao.getTotalSteps();
            double totalCalories = fitnessDao.getTotalCalories();

            requireActivity().runOnUiThread(() -> {
                tvTotalSteps.setText(String.valueOf(totalSteps));
                tvTotalCalories.setText(String.format("%.0f", totalCalories));
            });
        }).start();
    }

    private void saveUserData() {
        try {
            int dailyGoal = Integer.parseInt(etDailyGoal.getText().toString());
            double weight = Double.parseDouble(etWeight.getText().toString());
            double height = Double.parseDouble(etHeight.getText().toString());
            int age = Integer.parseInt(etAge.getText().toString());

            sharedPrefs.setDailyGoal(dailyGoal);
            sharedPrefs.setUserWeight(weight);
            sharedPrefs.setUserHeight(height);
            sharedPrefs.setUserAge(age);

            // Save to database as well
            UserGoals goals = new UserGoals(dailyGoal, dailyGoal * 7, weight, height, age, "male");
            new Thread(() -> {
                UserGoals existing = fitnessDao.getUserGoals();
                if (existing == null) {
                    fitnessDao.insertUserGoals(goals);
                } else {
                    fitnessDao.updateUserGoals(goals);
                }
            }).start();

        } catch (NumberFormatException e) {
            // Handle invalid input
        }
    }
}
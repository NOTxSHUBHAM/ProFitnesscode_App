package com.example.profitness.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.example.profitness.R;
import com.example.profitness.utils.SharedPrefs;

public class GoalsActivity extends AppCompatActivity {

    private EditText etDailyGoal;
    private Button btnSave;
    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        initializeViews();
        loadCurrentGoals();
    }

    private void initializeViews() {
        etDailyGoal = findViewById(R.id.et_daily_goal);
        btnSave = findViewById(R.id.btn_save);
        sharedPrefs = SharedPrefs.getInstance(this);

        btnSave.setOnClickListener(v -> saveGoals());
    }

    private void loadCurrentGoals() {
        etDailyGoal.setText(String.valueOf(sharedPrefs.getDailyGoal()));
    }

    private void saveGoals() {
        try {
            int dailyGoal = Integer.parseInt(etDailyGoal.getText().toString());
            sharedPrefs.setDailyGoal(dailyGoal);
            Toast.makeText(this, "Goals saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }
}
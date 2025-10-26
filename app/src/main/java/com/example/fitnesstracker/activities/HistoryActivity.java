package com.example.profitness.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.profitness.R;
import com.example.profitness.adapters.HistoryAdapter;
import com.example.profitness.database.AppDatabase;
import com.example.profitness.database.FitnessDao;
import com.example.profitness.database.entities.StepData;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private HistoryAdapter adapter;
    private FitnessDao fitnessDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initializeViews();
        initializeDatabase();
        loadHistoryData();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view_history);
        tvEmptyState = findViewById(R.id.tv_empty_state);

        adapter = new HistoryAdapter(Collections.emptyList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initializeDatabase() {
        AppDatabase database = AppDatabase.getDatabase(this);
        fitnessDao = database.fitnessDao();
    }

    private void loadHistoryData() {
        new Thread(() -> {
            List<StepData> stepDataList = fitnessDao.getAllStepData();
            runOnUiThread(() -> {
                if (stepDataList.isEmpty()) {
                    tvEmptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvEmptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.updateData(stepDataList);
                }
            });
        }).start();
    }
}
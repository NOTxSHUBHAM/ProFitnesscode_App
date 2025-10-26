package com.example.profitness.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.profitness.R;
import com.example.profitness.adapters.HistoryAdapter;
import com.example.profitness.database.AppDatabase;
import com.example.profitness.database.FitnessDao;
import com.example.profitness.database.entities.StepData;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvEmptyState;
    private HistoryAdapter adapter;
    private FitnessDao fitnessDao;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        initializeViews(view);
        initializeDatabase();
        loadHistoryData();

        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_history);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);

        adapter = new HistoryAdapter(Collections.emptyList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initializeDatabase() {
        AppDatabase database = AppDatabase.getDatabase(requireContext());
        fitnessDao = database.fitnessDao();
    }

    private void loadHistoryData() {
        executorService.execute(() -> {
            List<StepData> stepDataList = fitnessDao.getAllStepData();
            mainHandler.post(() -> {
                if (stepDataList.isEmpty()) {
                    tvEmptyState.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvEmptyState.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.updateData(stepDataList);
                }
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
package com.example.profitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.profitness.R;
import com.example.profitness.database.entities.StepData;
import com.example.profitness.utils.DateUtils;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<StepData> stepDataList;

    public HistoryAdapter(List<StepData> stepDataList) {
        this.stepDataList = stepDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StepData stepData = stepDataList.get(position);

        holder.tvDate.setText(DateUtils.getFormattedDate(new java.util.Date(stepData.getTimestamp())));
        holder.tvSteps.setText(String.valueOf(stepData.getStepCount()));

        // Add animation
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .alpha(1f)
                .setDuration(300)
                .setStartDelay(position * 100L)
                .start();
    }

    @Override
    public int getItemCount() {
        return stepDataList.size();
    }

    public void updateData(List<StepData> newData) {
        this.stepDataList = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvSteps;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvSteps = itemView.findViewById(R.id.tv_steps);
        }
    }
}
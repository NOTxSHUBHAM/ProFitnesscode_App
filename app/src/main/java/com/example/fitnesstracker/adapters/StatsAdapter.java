package com.example.profitness.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.profitness.R;
import com.example.profitness.models.Achievement;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {

    private List<Achievement> achievements;

    public StatsAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);

        holder.tvTitle.setText(achievement.getTitle());
        holder.tvDescription.setText(achievement.getDescription());

        if (achievement.isAchieved()) {
            holder.tvProgress.setText("Completed!");
            holder.tvProgress.setTextColor(holder.itemView.getContext().getColor(R.color.success_green));
        } else {
            int percentage = achievement.getProgressPercentage();
            holder.tvProgress.setText(percentage + "%");
            holder.tvProgress.setTextColor(holder.itemView.getContext().getColor(R.color.warning_orange));
        }

        // Animation
        holder.itemView.setTranslationY(50);
        holder.itemView.setAlpha(0f);
        holder.itemView.animate()
                .translationY(0)
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(position * 150L)
                .start();
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvProgress;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvProgress = itemView.findViewById(R.id.tv_progress);
        }
    }
}
package com.example.profitness.models;

public class Achievement {
    private String title;
    private String description;
    private int iconResId;
    private boolean achieved;
    private int progress;
    private int target;

    public Achievement(String title, String description, int iconResId, boolean achieved, int progress, int target) {
        this.title = title;
        this.description = description;
        this.iconResId = iconResId;
        this.achieved = achieved;
        this.progress = progress;
        this.target = target;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getIconResId() { return iconResId; }
    public void setIconResId(int iconResId) { this.iconResId = iconResId; }

    public boolean isAchieved() { return achieved; }
    public void setAchieved(boolean achieved) { this.achieved = achieved; }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }

    public int getTarget() { return target; }
    public void setTarget(int target) { this.target = target; }

    public int getProgressPercentage() {
        return (int) ((progress / (float) target) * 100);
    }
}
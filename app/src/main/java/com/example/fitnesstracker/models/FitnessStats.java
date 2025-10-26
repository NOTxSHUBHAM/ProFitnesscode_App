package com.example.profitness.models;

public class FitnessStats {
    private int totalSteps;
    private double totalDistance;
    private double totalCalories;
    private long totalActiveTime;
    private int dailyAverage;
    private int currentStreak;
    private int bestDaySteps;
    private int workoutsCompleted;

    public FitnessStats() {}

    public FitnessStats(int totalSteps, double totalDistance, double totalCalories,
                        long totalActiveTime, int dailyAverage, int currentStreak,
                        int bestDaySteps, int workoutsCompleted) {
        this.totalSteps = totalSteps;
        this.totalDistance = totalDistance;
        this.totalCalories = totalCalories;
        this.totalActiveTime = totalActiveTime;
        this.dailyAverage = dailyAverage;
        this.currentStreak = currentStreak;
        this.bestDaySteps = bestDaySteps;
        this.workoutsCompleted = workoutsCompleted;
    }

    // Getters and setters
    public int getTotalSteps() { return totalSteps; }
    public void setTotalSteps(int totalSteps) { this.totalSteps = totalSteps; }

    public double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(double totalDistance) { this.totalDistance = totalDistance; }

    public double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }

    public long getTotalActiveTime() { return totalActiveTime; }
    public void setTotalActiveTime(long totalActiveTime) { this.totalActiveTime = totalActiveTime; }

    public int getDailyAverage() { return dailyAverage; }
    public void setDailyAverage(int dailyAverage) { this.dailyAverage = dailyAverage; }

    public int getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(int currentStreak) { this.currentStreak = currentStreak; }

    public int getBestDaySteps() { return bestDaySteps; }
    public void setBestDaySteps(int bestDaySteps) { this.bestDaySteps = bestDaySteps; }

    public int getWorkoutsCompleted() { return workoutsCompleted; }
    public void setWorkoutsCompleted(int workoutsCompleted) { this.workoutsCompleted = workoutsCompleted; }
}
package com.example.profitness.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "step_data")
public class StepData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private int stepCount;
    private double distance;
    private double caloriesBurned;
    private long activeTime;
    private long timestamp;
    private int goalAchieved;

    public StepData(String date, int stepCount, double distance, double caloriesBurned, long activeTime, long timestamp) {
        this.date = date;
        this.stepCount = stepCount;
        this.distance = distance;
        this.caloriesBurned = caloriesBurned;
        this.activeTime = activeTime;
        this.timestamp = timestamp;
        this.goalAchieved = 0;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getStepCount() { return stepCount; }
    public void setStepCount(int stepCount) { this.stepCount = stepCount; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public long getActiveTime() { return activeTime; }
    public void setActiveTime(long activeTime) { this.activeTime = activeTime; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getGoalAchieved() { return goalAchieved; }
    public void setGoalAchieved(int goalAchieved) { this.goalAchieved = goalAchieved; }
}
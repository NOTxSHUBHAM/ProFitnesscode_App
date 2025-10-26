package com.example.profitness.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_sessions")
public class WorkoutSession {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String sessionType;
    private long startTime;
    private long endTime;
    private int steps;
    private double distance;
    private double calories;
    private double avgPace;
    private String routeData;

    public WorkoutSession(String sessionType, long startTime, long endTime,
                          int steps, double distance, double calories, double avgPace) {
        this.sessionType = sessionType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.steps = steps;
        this.distance = distance;
        this.calories = calories;
        this.avgPace = avgPace;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public int getSteps() { return steps; }
    public void setSteps(int steps) { this.steps = steps; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getAvgPace() { return avgPace; }
    public void setAvgPace(double avgPace) { this.avgPace = avgPace; }

    public String getRouteData() { return routeData; }
    public void setRouteData(String routeData) { this.routeData = routeData; }
}
package com.example.profitness.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_goals")
public class UserGoals {
    @PrimaryKey
    private int id = 1; // Singleton

    private int dailyStepGoal;
    private int weeklyStepGoal;
    private double weight;
    private double height;
    private int age;
    private String gender;

    public UserGoals(int dailyStepGoal, int weeklyStepGoal, double weight, double height, int age, String gender) {
        this.dailyStepGoal = dailyStepGoal;
        this.weeklyStepGoal = weeklyStepGoal;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDailyStepGoal() { return dailyStepGoal; }
    public void setDailyStepGoal(int dailyStepGoal) { this.dailyStepGoal = dailyStepGoal; }

    public int getWeeklyStepGoal() { return weeklyStepGoal; }
    public void setWeeklyStepGoal(int weeklyStepGoal) { this.weeklyStepGoal = weeklyStepGoal; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}